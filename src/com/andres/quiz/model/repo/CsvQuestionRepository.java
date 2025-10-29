package com.andres.quiz.model.repo;

import com.andres.quiz.model.question.Question;

import java.util.List;

public final class CsvQuestionRepository implements QuestionRepository {

    private final String csvPath;

    public CsvQuestionRepository(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Question> loadAll() {
        // TODO: abrir archivo, leer líneas, saltar cabecera y comentarios, mapear con
        // QuestionFactory, omitir filas inválidas.
        throw new UnsupportedOperationException("Not implemented");
    }
}
