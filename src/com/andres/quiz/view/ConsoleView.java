package com.andres.quiz.view;

import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.answer.Answer;

public interface ConsoleView {

    // Menú inicial
    void showWelcome();
    int chooseScoringStrategy(); // 1=Standard, 2=Negative, (3=Partial opcional)
    boolean chooseShuffle();
    Long chooseSeedOrNull(); // null si usuario no especifica

    // Por pregunta
    void showPrompt(String promptText);
    Answer readAnswerFor(Question q); // parsea formato e índices; reintenta hasta válido
    void showPerQuestionFeedback(Question q, Answer a, double obtained, double max, boolean correct, String explanation);

    // Resumen
    void showSummary(double totalObtained, double totalMax, int correctCount, int wrongCount);

    // Mensajes
    void info(String message);
    void error(String message);
}
