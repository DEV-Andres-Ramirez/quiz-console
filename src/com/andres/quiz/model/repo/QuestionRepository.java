package com.andres.quiz.model.repo;

import com.andres.quiz.model.question.Question;

import java.util.List;

public interface QuestionRepository {
    /**
     * Carga todas las preguntas disponibles.
     * Nunca retorna null. Puede retornar lista vacía.
     */
    List<Question> loadAll();
}
