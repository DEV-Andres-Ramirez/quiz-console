package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public final class NegativeScoring implements ScoringStrategy {
    
    private final double penalty;

    public NegativeScoring(double penalty) {
        if (penalty < 0)
            throw new IllegalArgumentException("penalty < 0");
        this.penalty = penalty;
    }

    @Override
    public double score(Question q, Answer a) {
        if (q == null || a == null)
            throw new IllegalArgumentException("q/a == null");
        return q.isCorrect(a) ? q.points() : -penalty;
    }
}
