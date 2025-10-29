package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public final class NegativeScoring implements ScoringStrategy {
    private final double penalty;

    public NegativeScoring(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public double score(Question q, Answer a) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }
}
