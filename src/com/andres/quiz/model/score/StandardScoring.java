package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public final class StandardScoring implements ScoringStrategy {
    
    @Override
    public double score(Question q, Answer a) {
        if (q == null || a == null)
            throw new IllegalArgumentException("q/a == null");
        return q.isCorrect(a) ? q.points() : 0.0;
    }
}
