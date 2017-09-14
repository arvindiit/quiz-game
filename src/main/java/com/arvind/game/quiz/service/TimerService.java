package com.arvind.game.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by klm75203 on 9/6/2017.
 */
@Component
public class TimerService {


    @Autowired
    AsyncService asyncService;

    AtomicInteger data = new AtomicInteger(31);

    public int getData(){
        return this.data.get();
    }

    public void start(){
        this.data = new AtomicInteger(5);
        asyncService.decrementInteger(this.data);

    }

    public void finish(){
        this.data = new AtomicInteger(100);
    }

    public void reset(){
        this.data = new AtomicInteger(31);
    }

}
