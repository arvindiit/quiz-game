package com.arvind.game.quiz.service;

import org.springframework.stereotype.Component;
import ui.Frame;

import java.util.HashMap;
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

}
