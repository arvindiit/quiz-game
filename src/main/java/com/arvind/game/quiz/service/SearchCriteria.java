package com.arvind.game.quiz.service;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by klm75203 on 9/6/2017.
 */
public class SearchCriteria {

    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
