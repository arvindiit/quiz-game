package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Question;
import com.arvind.game.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    EntityManager entityManager;

    Map<String, Iterator<Question>> userIterationmap = new HashMap<>();
    Map<Integer, String> questAnsMap = new HashMap<>();

    public Question getNextQuestion(String userId){
        Iterator<Question> it = userIterationmap.get(userId);
        if(it != null && it.hasNext()){
            return userIterationmap.get(userId).next();
        }else{
            return null;
        }
    }

    public void login(String userId){

        //List<Question> questionList = questionRepository.findAll();
        Query query  = entityManager.createQuery("SELECT questions from Question questions");
        query.setFirstResult(2);
        query.setMaxResults(4);
        List<Question> questionList = query.getResultList();
        userIterationmap.put(userId, questionList.iterator());
    }

    public void checkAnswer(Question answered, String userId){
        String rightAnswer = questAnsMap.get(answered.getId());
        if(answered.getAnswer() == null || !answered.getAnswer().equalsIgnoreCase(rightAnswer)) {
            System.out.println("WRONG asnwer!!!:No point for user: " + userId);
        }else{
            loginService.move(userId);
        }
    }

    @PostConstruct
    public void loadQuestions(){
        //List<Question> questionList = questionRepository.findAll();
        Query query  = entityManager.createQuery("SELECT questions from Question questions");
        query.setFirstResult(2);
        query.setMaxResults(4);
        List<Question> questionList = query.getResultList();
        questAnsMap = questionList.stream().collect(Collectors.toMap(Question::getId, Question::getAnswer));

    }
}
