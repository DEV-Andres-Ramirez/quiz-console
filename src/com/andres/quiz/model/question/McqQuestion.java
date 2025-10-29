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
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("options vacío o null");
        }
        if (correctIndexes == null || correctIndexes.isEmpty()) {
            throw new IllegalArgumentException("correctIndexes vacío o null");
        }
        this.options = List.copyOf(options);
        this.correctIndexes = Set.copyOf(correctIndexes);
    }

    public List<String> options() {
        return options;
    }

    public Set<Integer> correctIndexes() {
        return correctIndexes;
    }

    public int optionCount() {
        return options.size();
    }

    @Override
    public boolean isCorrect(Answer answer) {
        if (answer == null || !answer.isMcq())
            return false;
        return correctIndexes.equals(answer.selectedIndexes());
    }

    @Override
    public String renderPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append(text()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append(i).append(") ").append(options.get(i)).append("\n");
        }
        sb.append("Selecciona índice(s) 0-based separados por coma o punto y coma (ej: 1 o 0,2): ");
        return sb.toString();
    }
}
