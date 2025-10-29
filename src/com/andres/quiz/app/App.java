package com.andres.quiz.app;

import com.andres.quiz.controller.QuizController;
import com.andres.quiz.view.ConsoleView;
import com.andres.quiz.view.ConsoleIOView;
import com.andres.quiz.model.repo.QuestionRepository;
import com.andres.quiz.model.repo.CsvQuestionRepository;
import com.andres.quiz.model.score.ScoringStrategy;
import com.andres.quiz.model.score.StandardScoring;

public final class App {

    private App() {
    }

    public static void main(String[] args) {
        String csvPath = (args != null && args.length > 0 && args[0] != null && !args[0].isBlank())
                ? args[0].trim()
                : "resources/questions.csv";

        ConsoleView view = new ConsoleIOView();
        QuestionRepository repo = new CsvQuestionRepository(csvPath);
        ScoringStrategy strategy = new StandardScoring();

        QuizController controller = new QuizController(view, repo, strategy);
        controller.run();
    }
}
