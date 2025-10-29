package com.andres.quiz.app;

import com.andres.quiz.controller.QuizController;
import com.andres.quiz.view.ConsoleView;
import com.andres.quiz.view.ConsoleIOView;
import com.andres.quiz.model.repo.QuestionRepository;
import com.andres.quiz.model.repo.CsvQuestionRepository;
import com.andres.quiz.model.score.ScoringStrategy;
import com.andres.quiz.model.score.StandardScoring;
import com.andres.quiz.model.iter.QuestionIterator;
// (Opcional) import com.andres.quiz.model.obs.QuizObserver;

public final class App {
    
    private App() {
    }

    public static void main(String[] args) {
        // TODO: ensamblar dependencias reales aquí (sin lógica de negocio).
        ConsoleView view = new ConsoleIOView();
        QuestionRepository repo = new CsvQuestionRepository("resources/questions.csv");
        ScoringStrategy strategy = new StandardScoring();
        QuestionIterator iterator = null; // se definirá en Controller

        QuizController controller = new QuizController(view, repo, strategy);
        // Opcional: registrar observadores
        // controller.addObserver(...);

        controller.run(); // orquesta el ciclo del quiz
    }
}
