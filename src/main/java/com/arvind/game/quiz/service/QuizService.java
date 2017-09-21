package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Question;
import com.arvind.game.quiz.repository.QuestionRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by klm75203 on 9/7/2017.
 */
@Component
public class QuizService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    TimerService timerService;

    @Autowired
    EntityManager entityManager;

    volatile int finishedPlayer = 0;
    volatile int loggedInPlayers = 0;
    volatile int allAnswered = 0;
    List<String> winners = new ArrayList<>();

    Map<String, Iterator<Question>> userIterationmap = new HashMap<>();
    Map<Integer, String> questAnsMap = new HashMap<>();
    Map<String, Integer> winnersMap = new HashMap<>();
    volatile int questionStart = 0;
    volatile boolean pushNextQuestion = true;
    String answer = "";

    int questionNo = 0;
    public Question getNextQuestion(String userId){

            Iterator<Question> it = userIterationmap.get(userId);
            if (it != null && it.hasNext()) {
                Question question = userIterationmap.get(userId).next();
                synchronized (this) {
                    if (pushNextQuestion) {
                        pushNextQuestion = false;
                        loginService.pushQuestion(question, ++questionNo);
                        answer = question.getAnswer();
                    }
                }
                return question;
            } else {
                finishedPlayer++;
                ifAllFinishedDeclareWinner();
                return null;
            }
    }

    public void login(String userId){
        winnersMap.put(userId, 0);
        userIterationmap.put(userId, getQuestionList().iterator());
        loggedInPlayers++;
    }

    public boolean checkAnswer(Question answered, String userId){

        boolean isRight = false;
        String rightAnswer = questAnsMap.get(answered.getId());
        if(answered.getAnswer() == null || !answered.getAnswer().equalsIgnoreCase(rightAnswer)) {
            System.out.println("WRONG answer!!!:No point for user: " + userId);
        }else{
            loginService.move(userId);
            int points = winnersMap.get(userId)+1;
            winnersMap.put(userId, points);
            if(points >= 10){
                userIterationmap = new HashMap<>();
                winners.add(userId);
            }
            isRight = true;
        }

        allAnswered = allAnswered+1;
        while(this.allAnswered != loggedInPlayers){

        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pushNextQuestion = true;
        loginService.pushAnswer(answer);
        allAnswered = 0;

        return isRight;
    }

    private void ifAllFinishedDeclareWinner(){
        if(finishedPlayer == loggedInPlayers) {
            timerService.finish();
            if(winners.size() != 0) {
                loginService.makeWinner(winners);
            }
        }
    }

    public void reset(){

        finishedPlayer = 0;
        loggedInPlayers = 0;
        winners = new ArrayList<>();
        userIterationmap = new HashMap<>();
        winnersMap = new HashMap<>();

        questAnsMap = getQuestionList().stream().collect(Collectors.toMap(Question::getId, Question::getAnswer));
        timerService.reset();

    }

    @PostConstruct
    public void start(){

        reset();
    }

    private List<Question> getQuestionList(){
        Query query1  = entityManager.createQuery("SELECT questions from Question questions");
        query1.setFirstResult(questionStart);
        query1.setMaxResults(10);
        List<Question> questionList1 = new ArrayList<>(query1.getResultList());

        Query query2  = entityManager.createQuery("SELECT questions from Question questions");
        query2.setFirstResult(questionStart+50);
        query2.setMaxResults(5);
        List<Question> questionList2 = new ArrayList<>(query2.getResultList());

        Query query3  = entityManager.createQuery("SELECT questions from Question questions");
        query3.setFirstResult(questionStart+10);
        query3.setMaxResults(5);
        List<Question> questionList3 = new ArrayList<>(query3.getResultList());

        questionList1.addAll(questionList2);
        questionList1.addAll(questionList3);

        return questionList1;
    }
}
