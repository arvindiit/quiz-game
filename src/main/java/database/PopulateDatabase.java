package database;

import org.springframework.web.client.RestTemplate;

import java.util.Base64;

public class PopulateDatabase {
    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
       // QuizQuestions quote = restTemplate.getForObject("https://opentdb.com/api.php?amount=50&category=11&difficulty=easy&type=multiple", QuizQuestions.class);
        System.out.println(
                new String(Base64.getDecoder().decode(
                        "SW4gYW55IHByb2dyYW1taW5nIGxhbmd1YWdlLCB3aGF0IGlzIHRoZSBtb3N0IGNvbW1vbiB3YXkgdG8gaXRlcmF0ZSB0aHJvdWdoIGFuIGFycmF5Pw==".getBytes())));

        System.out.println("helllo");
    }


}
