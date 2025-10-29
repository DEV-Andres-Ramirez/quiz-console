package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public interface ScoringStrategy {
    double score(Question q, Answer a);
}
