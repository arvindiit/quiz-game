package com.arvind.game.quiz.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by klm75203 on 9/8/2017.
 */
@Entity
public class Option {

    @Id
    int id;
    String value;

    @ManyToOne
    Question question;

    public Option() {
    }

    public Option(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
