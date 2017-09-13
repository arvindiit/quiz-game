package com.arvind.game.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by klm75203 on 9/6/2017.
 */

@Controller
public class TimerController {


    @Autowired
    TimerService timerService;

    @GetMapping("/getTimeValue")
    public ResponseEntity<?> getTimerResultViaAjax(HttpServletRequest request) {
        return ResponseEntity.ok(timerService.getData());

    }

}
