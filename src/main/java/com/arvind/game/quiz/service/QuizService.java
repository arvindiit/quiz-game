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

    int finishedPlayer = 0;
    int loggedInPlayers = 0;
    int allAnswered = 0;
    List<String> winners = new ArrayList<>();

    Map<String, Iterator<Question>> userIterationmap = new HashMap<>();
    Map<Integer, String> questAnsMap = new HashMap<>();
    Map<String, Integer> winnersMap = new HashMap<>();
    int questionStart = -15;
    boolean pushNextQuestion = true;
    String answer = "";

    int questionNo = 0;
    public synchronized Question getNextQuestion(String userId){

        while(allAnswered != 0){
            System.out.println("waiting for everyone to finish");
        }

        Iterator<Question> it = userIterationmap.get(userId);
        if(it != null && it.hasNext()){
            Question question =  userIterationmap.get(userId).next();
            if(pushNextQuestion){
                pushNextQuestion = false;
                loginService.pushQuestion(question, ++questionNo);
                answer = question.getAnswer();
            }
            return question;
        }else{
            finishedPlayer++;
            ifAllFinishedDeclareWinner();
            return null;
        }
    }

    public void login(String userId){

        Query query  = entityManager.createQuery("SELECT questions from Question questions");
        query.setFirstResult(questionStart);
        query.setMaxResults(15);
        List<Question> questionList = new ArrayList<>(query.getResultList());
        winnersMap.put(userId, 0);
        userIterationmap.put(userId, questionList.iterator());
        loggedInPlayers++;
    }

    public synchronized boolean checkAnswer(Question answered, String userId){
        allAnswered++;
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

        if(allAnswered == loggedInPlayers){
            allAnswered = 0;
            pushNextQuestion = true;
            loginService.pushAnswer(answer);
        }

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
        questionStart = questionStart+15;
        userIterationmap = new HashMap<>();
        winnersMap = new HashMap<>();
        Query query  = entityManager.createQuery("SELECT questions from Question questions");
        query.setFirstResult(questionStart);
        query.setMaxResults(15);
        List<Question> questionList = query.getResultList();
        questAnsMap = questionList.stream().collect(Collectors.toMap(Question::getId, Question::getAnswer));
        timerService.reset();

    }

    @PostConstruct
    public void start(){

        reset();
    }
}
