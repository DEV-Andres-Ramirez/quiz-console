package com.andres.quiz.model.factory;

import com.andres.quiz.model.question.Difficulty;
import com.andres.quiz.model.question.Question;

public final class QuestionFactory {

    private QuestionFactory() {
    }

    /**
     * Construye una Question concreta (MCQ o VF) a partir de una fila CSV.
     * Espera columnas en orden:
     * type,id,difficulty,points,question,options,answer,explanation
     * Lanza IllegalArgumentException si la fila es inválida.
     */
    public static Question fromCsvRow(String[] cols) {
        // TODO: validar columnas, parsear y construir
        throw new UnsupportedOperationException("Not implemented");
    }

    public static Difficulty parseDifficulty(String token) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }
}
