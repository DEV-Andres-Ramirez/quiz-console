package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public final class PartialCreditScoring implements ScoringStrategy {
    private final double penaltyWrong;

    public PartialCreditScoring(double penaltyWrong) {
        this.penaltyWrong = penaltyWrong;
    }

    @Override
    public double score(Question q, Answer a) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }
}
