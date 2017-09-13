package com.arvind.game.quiz.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by klm75203 on 9/6/2017.
 */
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(unique=true)
    String title;
    String answer;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Option> options;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question(String title) {
        this.title = title;
    }

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
}
