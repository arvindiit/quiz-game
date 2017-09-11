package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by klm75203 on 9/6/2017.
 */
@Controller
public class QuizController {

    @Autowired
    QuizService quizService;


    @GetMapping("/quiz")
    public void quiz(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getCookies()[0].getValue());
        response.encodeRedirectURL("/quiz-page");

    }

    @GetMapping("/quiz-page")
    public String quizPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        if(request.getCookies() == null || request.getCookies()[0] == null){
            return "/monster";
        }
        String userId = request.getCookies()[0].getValue();
        Question questionAsked = quizService.getNextQuestion(userId);
        model.addAttribute(model.addAttribute("question", questionAsked));
        if(questionAsked == null){
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            return "/thankYou";
        }
        return "/quiz";
    }



    @PostMapping("/quiz-started")
    public RedirectView postAnswer(@ModelAttribute Question question, HttpServletRequest request) {
        String userId = request.getCookies()[0].getValue();
        quizService.checkAnswer(question, userId);

        return new RedirectView("/quiz-page", true);

    }


}
