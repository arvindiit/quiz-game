package com.arvind.game.quiz.repository;

import com.arvind.game.quiz.domain.Option;
import com.arvind.game.quiz.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {


}
