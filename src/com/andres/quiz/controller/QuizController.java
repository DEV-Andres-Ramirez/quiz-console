package com.andres.quiz.controller;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.iter.QuestionIterator;
import com.andres.quiz.model.iter.SequentialQuestionIterator;
import com.andres.quiz.model.iter.ShuffledQuestionIterator;
import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.repo.QuestionRepository;
import com.andres.quiz.model.score.ScoringStrategy;
import com.andres.quiz.view.ConsoleView;

import java.util.List;
// import com.andres.quiz.model.obs.QuizObserver;
import java.util.ArrayList;

public final class QuizController {

    private final ConsoleView view;
    private final QuestionRepository repository;
    private final ScoringStrategy scoring;
    // private final List<QuizObserver> observers = new ArrayList<>();

    public QuizController(ConsoleView view, QuestionRepository repository, ScoringStrategy scoring) {
        this.view = view;
        this.repository = repository;
        this.scoring = scoring;
    }

    // public void addObserver(QuizObserver o) { observers.add(o); }

    public void run() {
        // TODO: cargar preguntas, pedir modo (secuencial/barajado), crear iterador,
        // ciclo principal, feedback y resumen.
        throw new UnsupportedOperationException("Not implemented");
    }

    private QuestionIterator buildIterator(List<Question> questions, boolean shuffled, Long seed) {
        // TODO: devolver SequentialQuestionIterator o ShuffledQuestionIterator según
        // flags.
        throw new UnsupportedOperationException("Not implemented");
    }

    private Answer readAnswerFor(Question q) {
        // TODO: delegar en la vista la lectura/validación de la respuesta y devolver
        // Answer.
        throw new UnsupportedOperationException("Not implemented");
    }
}
