package com.andres.quiz.view;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

import java.util.Scanner;

public final class ConsoleIOView implements ConsoleView {

    private final Scanner in = new Scanner(System.in);

    @Override
    public void showWelcome() {
        // TODO: imprimir mensaje de bienvenida
    }

    @Override
    public int chooseScoringStrategy() {
        // TODO: leer opción y validar
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean chooseShuffle() {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Long chooseSeedOrNull() {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void showPrompt(String promptText) {
        // TODO
    }

    @Override
    public Answer readAnswerFor(Question q) {
        // TODO: leer entrada (VF: T/F; MCQ: 0,2 / 0;2), validar y normalizar a Answer
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void showPerQuestionFeedback(Question q, Answer a, double obtained, double max, boolean correct,
            String explanation) {
        // TODO
    }

    @Override
    public void showSummary(double totalObtained, double totalMax, int correctCount, int wrongCount) {
        // TODO
    }

    @Override
    public void info(String message) {
        // TODO
    }

    @Override
    public void error(String message) {
        // TODO
    }
}
