package com.andres.quiz.model.question;

import com.andres.quiz.model.answer.Answer;

public abstract class Question {
    private final String id;
    private final String text;
    private final Difficulty difficulty;
    private final double points;
    private final String explanation;

    protected Question(String id, String text, Difficulty difficulty, double points, String explanation) {
        this.id = id;
        this.text = text;
        this.difficulty = difficulty;
        this.points = points;
        this.explanation = explanation;
    }

    public String id() {
        return id;
    }

    public String text() {
        return text;
    }

    public Difficulty difficulty() {
        return difficulty;
    }

    public double points() {
        return points;
    }

    public String explanation() {
        return explanation;
    }

    public abstract boolean isCorrect(Answer answer);

    /** Texto que verá la vista como prompt (incluye opciones si aplica). */
    public abstract String renderPrompt();
}
