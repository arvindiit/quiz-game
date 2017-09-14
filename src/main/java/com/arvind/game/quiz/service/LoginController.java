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
    public Object welcome() {
        if(timerService.getData()<=0){
            return ResponseEntity.ok(formResponse("You are late. Game is already in progress !!!!!!!!!!"));
        }

        if(timerService.getData()==100){
            return ResponseEntity.ok(formResponse("Please request the admin to reset the game to start playing !!!!!"));
        }

        return "/home";
        }



    @PostMapping("/login")
    public ResponseEntity<?> loginResultViaAjax(
            @Valid @RequestBody String userName, HttpServletResponse response) {

        userName = userName.toUpperCase();
        if(loginService.doesUserExist(userName)){
            return ResponseEntity.ok(50);
        }

        if(loginService.getPlayerNo() >=5){
            return ResponseEntity.ok(formResponse("Sorry. There are already enough people in the game !!!!!!!!!!"));
        }

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.addCookie(new Cookie("user", userName));
        quizService.login(userName);
        loginService.addPlayer(userName);
        return ResponseEntity.ok(timerService.getData());
    }

    private String formResponse(String text){
        String str = "<div class=\"container\" style=\"height:400px\">" +
                "<label style=\"font-size:50px\">"+text+"</label></div>";
        return str;
    }

}
