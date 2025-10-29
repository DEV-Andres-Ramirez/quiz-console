package com.andres.quiz.model.obs;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Difficulty;
import com.andres.quiz.model.question.McqQuestion;
import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.question.TrueFalseQuestion;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class QuizStatistics implements QuizObserver {

    private int totalAnswered = 0;
    private int totalCorrect = 0;
    private double pointsTotal = 0.0;

    private final Map<String, Integer> correctByType = new HashMap<>();
    private final Map<String, Integer> totalByType = new HashMap<>();

    private final Map<Difficulty, Integer> correctByDiff = new EnumMap<>(Difficulty.class);
    private final Map<Difficulty, Integer> totalByDiff = new EnumMap<>(Difficulty.class);

    public QuizStatistics() {
        for (Difficulty d : Difficulty.values()) {
            correctByDiff.put(d, 0);
            totalByDiff.put(d, 0);
        }
        totalByType.put(typeName(McqQuestion.class), 0);
        totalByType.put(typeName(TrueFalseQuestion.class), 0);
        correctByType.put(typeName(McqQuestion.class), 0);
        correctByType.put(typeName(TrueFalseQuestion.class), 0);
    }

    @Override
    public void onQuestionAnswered(Question q, Answer a, double score) {
        totalAnswered++;
        pointsTotal += score;

        String t = typeName(q.getClass());
        totalByType.merge(t, 1, Integer::sum);
        totalByDiff.merge(q.difficulty(), 1, Integer::sum);

        boolean isCorrect = q.isCorrect(a);
        if (isCorrect) {
            totalCorrect++;
            correctByType.merge(t, 1, Integer::sum);
            correctByDiff.merge(q.difficulty(), 1, Integer::sum);
        }
    }

    public int totalAnswered() {
        return totalAnswered;
    }

    public int totalCorrect() {
        return totalCorrect;
    }

    public int totalWrong() {
        return totalAnswered - totalCorrect;
    }

    public double pointsTotal() {
        return pointsTotal;
    }

    public Map<String, Integer> correctByType() {
        return Map.copyOf(correctByType);
    }

    public Map<String, Integer> totalByType() {
        return Map.copyOf(totalByType);
    }

    public Map<Difficulty, Integer> correctByDiff() {
        return Map.copyOf(correctByDiff);
    }

    public Map<Difficulty, Integer> totalByDiff() {
        return Map.copyOf(totalByDiff);
    }

    private static String typeName(Class<?> cls) {
        if (cls == null)
            return "Unknown";
        if (cls.getSimpleName().isEmpty())
            return cls.getName();
        return cls.getSimpleName();
    }
}
