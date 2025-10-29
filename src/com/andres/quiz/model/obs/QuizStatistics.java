package com.andres.quiz.model.obs;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public final class QuizStatistics implements QuizObserver {

    public QuizStatistics() {
    }

    @Override
    public void onQuestionAnswered(Question q, Answer a, double score) {
        // TODO: acumular métricas (aciertos, fallos, totales, por tipo, etc.)
        throw new UnsupportedOperationException("Not implemented");
    }

    // TODO: exponer lecturas de métricas (getters) para el resumen
}
