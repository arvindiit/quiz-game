package com.arvind.game.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by klm75203 on 9/6/2017.
 */
@Controller
public class LoginController {

    @Autowired
    TimerService timerService;

    @Autowired
    LoginService loginService;

    @Autowired
    QuizService quizService;

    @GetMapping("/")
    public String welcome(Model model) {
        return "/home";
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginResultViaAjax(
            @Valid @RequestBody String userName, HttpServletResponse response, HttpServletRequest request) {

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.addCookie(new Cookie("user", userName));
        if(timerService.getData()>0) {
            quizService.login(userName);
            loginService.addPlayer(userName);
            return ResponseEntity.ok(timerService.getData());
        }else{
            return ResponseEntity.ok("You are late. Game is already in progress !!!!!!!!!!");
        }

    }

}
