package com.arvind.game.quiz.service;

import com.arvind.game.quiz.domain.Option;
import com.arvind.game.quiz.domain.Question;
import com.arvind.game.quiz.repository.OptionRepository;
import com.arvind.game.quiz.repository.QuestionRepository;
import database.QuizQuestion;
import database.QuizQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

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
        loginService.reset();
        quizService.reset();
        return ResponseEntity.ok("success");
    }

    @GetMapping("/admin/nextQuiz")
    public String nextQuiz() {
        return "/nextQuiz";
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

    @GetMapping("/populateDb")
    public ResponseEntity<?> populateDb() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.defaultCharset()));

        QuizQuestions questions = restTemplate.getForObject("https://opentdb.com/api.php?amount=20&category=24&type=multiple&encode=base64", QuizQuestions.class);

        questionRepository.save(populatequestions(questions));
        return ResponseEntity.ok("success");
    }

    private List<Question> populatequestions(QuizQuestions questions){
        List<Question> questionList = new ArrayList<>();
        for(QuizQuestion quizQuestion : questions.getResults()){
            try {
                Question question = new Question();
                question.setOptions(new HashSet<>());
                question.setTitle(getStringFromBase64Encoded(quizQuestion.getQuestion()));
                question.setAnswer(getStringFromBase64Encoded(quizQuestion.getCorrect_answer()));

                Option option1 = new Option(question.getAnswer());
                option1.setQuestion(question);
                question.getOptions().add(option1);
                for (String str : quizQuestion.getIncorrect_answers()) {
                    Option option2 = new Option(getStringFromBase64Encoded(str));
                    option2.setQuestion(question);
                    question.getOptions().add(option2);
                }

                questionList.add(question);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return questionList;
    }

    private String getStringFromBase64Encoded(String encodedStr) throws Exception {
        try {
            return new String(Base64.getDecoder().decode(encodedStr.getBytes(StandardCharsets.UTF_8)));
        }catch(Exception e){
            System.out.println("Error is: "+encodedStr);
            throw new Exception(e);
        }

    }


}
