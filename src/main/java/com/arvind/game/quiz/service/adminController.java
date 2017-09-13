package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Option;
import com.arvind.game.quiz.domain.Question;
import com.arvind.game.quiz.repository.OptionRepository;
import com.arvind.game.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class adminController {

    @Autowired
    TimerService timerService;

    @Autowired
    LoginService loginService;

    @Autowired
    QuizService quizService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    OptionRepository optionRepository;

    @GetMapping("/admin")
    public String admin(Model model){

        Question question = new Question();
        ArrayList<Option> list = new ArrayList<>();
        list.add(new Option());
        list.add(new Option());
        list.add(new Option());
        model.addAttribute("question", question);
        model.addAttribute("options", list);

        return "/admin";
    }

    @GetMapping("/admin/quiz-start")
    public ResponseEntity<?> welcome() {
        timerService.start();
        return ResponseEntity.ok("success");
    }
    @GetMapping("/admin/reset")
    public ResponseEntity<?> reset() {
        timerService.start();
        loginService.reset();
        quizService.reset();
        return ResponseEntity.ok("success");
    }

    @PostMapping("/questions-post")
    public String postQuestion(Question question,
                               @RequestParam(value = "value" , required = false) String[] value) {


        question.setOptions(new HashSet<>());
        Option option1 = new Option(question.getAnswer());
        option1.setQuestion(question);
        question.getOptions().add(option1);

        Option option2 = new Option(value[0]);
        option2.setQuestion(question);
        question.getOptions().add(option2);

        Option option3 = new Option(value[1]);
        option3.setQuestion(question);
        question.getOptions().add(option3);

        Option option4 = new Option(value[2]);
        option4.setQuestion(question);
        question.getOptions().add(option4);

        questionRepository.saveAndFlush(question);
        return "redirect:/admin";
    }


}
