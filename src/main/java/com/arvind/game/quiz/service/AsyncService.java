package com.arvind.game.quiz.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by klm75203 on 9/6/2017.
 */
@Async("daemonThreadPoolTaskExecutor")
@Component
public class AsyncService {

    public void decrementInteger(AtomicInteger i){

        while(i.get() > 0){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i.set(i.get()-1);
        }
    }

}
