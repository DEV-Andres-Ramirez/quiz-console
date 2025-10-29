package com.andres.quiz.controller;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.iter.QuestionIterator;
import com.andres.quiz.model.iter.SequentialQuestionIterator;
import com.andres.quiz.model.iter.ShuffledQuestionIterator;
import com.andres.quiz.model.obs.QuizStatistics;
import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.repo.QuestionRepository;
import com.andres.quiz.model.score.NegativeScoring;
import com.andres.quiz.model.score.PartialCreditScoring;
import com.andres.quiz.model.score.ScoringStrategy;
import com.andres.quiz.view.ConsoleView;

import java.util.ArrayList;
import java.util.List;

public final class QuizController {

    private final ConsoleView view;
    private final QuestionRepository repository;
    private final ScoringStrategy defaultScoring;

    public QuizController(ConsoleView view, QuestionRepository repository, ScoringStrategy scoring) {
        if (view == null || repository == null || scoring == null) {
            throw new IllegalArgumentException("Dependencias nulas no permitidas");
        }
        this.view = view;
        this.repository = repository;
        this.defaultScoring = scoring;
    }

    public void run() {
        view.showWelcome();

        List<Question> questions = repository.loadAll();
        if (questions.isEmpty()) {
            view.error("No se pudieron cargar preguntas desde el CSV. Verifica el archivo.");
            return;
        }
        double totalMax = questions.stream().mapToDouble(Question::points).sum();

        int scoringChoice = view.chooseScoringStrategy();
        ScoringStrategy scoring = chooseScoring(scoringChoice);

        boolean shuffled = view.chooseShuffle();
        Long seed = shuffled ? view.chooseSeedOrNull() : null;

        QuestionIterator it = buildIterator(questions, shuffled, seed);
        QuizStatistics stats = new QuizStatistics();
        List<Question> failedQuestions = new ArrayList<>();
        List<Answer> failedAnswers = new ArrayList<>();

        double totalObtained = 0.0;
        int correctCount = 0;
        int wrongCount = 0;

        while (it.hasNext()) {
            Question q = it.next();

            view.showPrompt(q.renderPrompt());

            Answer a = view.readAnswerFor(q);

            double obtained = scoring.score(q, a);
            boolean correct = q.isCorrect(a);
            totalObtained += obtained;

            if (correct)
                correctCount++;
            else {
                wrongCount++;
                failedQuestions.add(q);
                failedAnswers.add(a);
            }

            stats.onQuestionAnswered(q, a, obtained);

            view.showPerQuestionFeedback(q, a, obtained, q.points(), correct, q.explanation());
        }

        view.showSummary(totalObtained, totalMax, correctCount, wrongCount);

        if (!failedQuestions.isEmpty()) {
            view.info("—— Preguntas falladas ———————————————————————");
            for (int i = 0; i < failedQuestions.size(); i++) {
                Question q = failedQuestions.get(i);
                view.info((i + 1) + ") [" + q.id() + "] " + q.text());
                if (q.explanation() != null && !q.explanation().isBlank()) {
                    view.info("   Explicación: " + q.explanation());
                }
            }
        } else {
            view.info("¡Excelente! No fallaste ninguna pregunta.");
        }

        view.info("—— Estadísticas ——————————————————————————————");
        view.info("Total respondidas: " + stats.totalAnswered() + " | Aciertos: " + stats.totalCorrect() + " | Fallos: "
                + stats.totalWrong());
        view.info(String.format("Puntos acumulados (según estrategia): %.2f", stats.pointsTotal()));

        var totalByType = stats.totalByType();
        var correctByType = stats.correctByType();
        for (String type : totalByType.keySet()) {
            int tot = totalByType.get(type);
            int cor = correctByType.getOrDefault(type, 0);
            double pct = tot == 0 ? 0.0 : (cor * 100.0 / tot);
            view.info(String.format("Tipo %-12s → %d/%d correctas (%.1f%%)", type, cor, tot, pct));
        }

        var totalByDiff = stats.totalByDiff();
        var correctByDiff = stats.correctByDiff();
        for (var diff : totalByDiff.keySet()) {
            int tot = totalByDiff.get(diff);
            int cor = correctByDiff.getOrDefault(diff, 0);
            double pct = tot == 0 ? 0.0 : (cor * 100.0 / tot);
            view.info(String.format("Dificultad %-6s → %d/%d correctas (%.1f%%)", diff, cor, tot, pct));
        }
    }

    private ScoringStrategy chooseScoring(int choice) {
        return switch (choice) {
            case 1 -> defaultScoring;
            case 2 -> new NegativeScoring(0.25);
            case 3 -> new PartialCreditScoring(0.10);
            default -> defaultScoring;
        };
    }

    private QuestionIterator buildIterator(List<Question> questions, boolean shuffled, Long seed) {
        if (!shuffled)
            return new SequentialQuestionIterator(questions);
        long effectiveSeed = (seed != null) ? seed : System.nanoTime();
        return new ShuffledQuestionIterator(questions, effectiveSeed);
    }
}
