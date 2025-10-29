package com.andres.quiz.view;

import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.answer.Answer;

public interface ConsoleView {

    void showWelcome();

    int chooseScoringStrategy();

    boolean chooseShuffle();

    Long chooseSeedOrNull();

    void showPrompt(String promptText);

    Answer readAnswerFor(Question q);

    void showPerQuestionFeedback(Question q, Answer a, double obtained, double max, boolean correct,
            String explanation);

    void showSummary(double totalObtained, double totalMax, int correctCount, int wrongCount);

    void info(String message);

    void error(String message);
}
