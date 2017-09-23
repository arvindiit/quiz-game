package com.arvind.game.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    public Object welcome(Model model) {
        if(timerService.getData()<=0){
            return ResponseEntity.ok(formResponse("You are late. Game is already in progress !!!!!!!!!!"));
        }

        if(timerService.getData()==100){
            return ResponseEntity.ok(formResponse("Please request the admin to reset the game to start playing !!!!!"));
        }

        model.addAttribute("searchResult", new SearchResult());
        return "/home";
        }



    @PostMapping("/login")
    public String loginResultViaAjax(Model model,
                                           @ModelAttribute(value="searchResult") SearchResult searchResult, HttpServletRequest request, HttpServletResponse response) {

        String userName = searchResult.getUserName().toUpperCase();
        int data;
        if(loginService.doesUserExist(userName)){
            data = 50;
        } else if(loginService.getPlayerNo() >=4){
            data = 51;
        }else {

            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.addCookie(new Cookie("user", userName));
            quizService.login(userName);
            loginService.addPlayer(userName);

            data = timerService.getData();
            model.addAttribute("user", userName);
        }
        model.addAttribute("data", data);
        return "/loggedIn";
    }

    @GetMapping("/loggedIn")
    public String loggedIn(Model model) {
        return "/loggedIn";
    }

    private String formResponse(String text){
        String str = "<div class=\"container\" style=\"height:400px\">" +
                "<label style=\"font-size:50px\">"+text+"</label></div>";
        return str;
    }

}
