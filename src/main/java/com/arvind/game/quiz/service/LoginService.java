package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Question;
import org.springframework.stereotype.Component;
import ui.Frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klm75203 on 9/10/2017.
 */
@Component
public class LoginService {

    Frame frame = new Frame();
    Map<String, Integer> map = new HashMap<>();

    int playerNo = 0;
    public void addPlayer(String name){
        ++playerNo;
        map.put(name, playerNo);
        frame.addPlayer(playerNo, name);
    }

    public void move(String name){
        int playerNo = map.get(name);
        frame.move(playerNo);
    }

    public void makeWinner(List<String> userIds){
        frame.makeWinner(userIds);
    }

    public int getPlayerNo(){
        return this.playerNo;
    }

    public void reset(){
        frame.dispose();
        frame = new Frame();
        map = new HashMap<>();
        playerNo = 0;
    }

    public void pushQuestion(Question question, int qNo){
        frame.pushQuestion(question, qNo);
    }

    public void pushAnswer(String answer){
        frame.pushAnswer(answer);
    }

    public boolean doesUserExist(String userId){
        return map.get(userId) != null;
    }

}
