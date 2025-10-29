package com.andres.quiz.model.question;

import com.andres.quiz.model.answer.Answer;

import java.util.List;
import java.util.Set;

public final class McqQuestion extends Question {
    private final List<String> options;
    private final Set<Integer> correctIndexes;

    public McqQuestion(String id, String text, Difficulty difficulty, double points, String explanation,
                       List<String> options, Set<Integer> correctIndexes) {
        super(id, text, difficulty, points, explanation);
        this.options = options;
        this.correctIndexes = correctIndexes;
    }

    public List<String> options() { return options; }
    public Set<Integer> correctIndexes() { return correctIndexes; }
    public int optionCount() { return options == null ? 0 : options.size(); }

    @Override
    public boolean isCorrect(Answer answer) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String renderPrompt() {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }
}
