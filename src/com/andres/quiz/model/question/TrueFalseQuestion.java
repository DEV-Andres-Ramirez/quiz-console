package com.andres.quiz.model.question;

import com.andres.quiz.model.answer.Answer;

public final class TrueFalseQuestion extends Question {

    private final boolean correct;

    public TrueFalseQuestion(String id, String text, Difficulty difficulty, double points, String explanation,
            boolean correct) {
        super(id, text, difficulty, points, explanation);
        this.correct = correct;
    }

    public boolean correct() {
        return correct;
    }

    @Override
    public boolean isCorrect(Answer answer) {
        if (answer == null || answer.isMcq())
            return false;
        return answer.trueFalse().orElse(false) == correct;
    }

    @Override
    public String renderPrompt() {
        return text() + " (T/F): ";
    }
}
