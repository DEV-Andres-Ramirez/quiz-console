package com.andres.quiz.model.score;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.McqQuestion;
import com.andres.quiz.model.question.Question;

import java.util.Set;

public final class PartialCreditScoring implements ScoringStrategy {

    private final double penaltyWrong;

    public PartialCreditScoring(double penaltyWrong) {
        if (penaltyWrong < 0)
            throw new IllegalArgumentException("penaltyWrong < 0");
        this.penaltyWrong = penaltyWrong;
    }

    @Override
    public double score(Question q, Answer a) {
        if (q == null || a == null)
            throw new IllegalArgumentException("q/a == null");

        // Para VF: comportamiento estándar
        if (!(q instanceof McqQuestion mcq)) {
            return q.isCorrect(a) ? q.points() : 0.0;
        }

        // MCQ con crédito parcial
        Set<Integer> correct = mcq.correctIndexes();
        Set<Integer> chosen = a.selectedIndexes();

        if (correct.isEmpty() || chosen.isEmpty())
            return 0.0;

        long hits = chosen.stream().filter(correct::contains).count();
        long wrongs = chosen.stream().filter(ix -> !correct.contains(ix)).count();

        double portion = (double) hits / (double) correct.size();
        double score = q.points() * portion - (wrongs * penaltyWrong);

        return Math.max(0.0, score);
    }
}
