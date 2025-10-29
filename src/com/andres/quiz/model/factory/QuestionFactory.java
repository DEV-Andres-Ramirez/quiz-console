package com.andres.quiz.model.factory;

import com.andres.quiz.model.question.Difficulty;
import com.andres.quiz.model.question.McqQuestion;
import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.question.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class QuestionFactory {

    private QuestionFactory() {
    }

    public static Question fromCsvRow(String[] c) {
        require(c != null && c.length >= 7, "CSV: columnas insuficientes (mínimo 7)");
        final String type = trim(c[0]);
        final String id = trim(c[1]);
        final Difficulty difficulty = parseDifficulty(trim(c[2]));
        final double points = parsePositiveDouble(trim(c[3]), "CSV: points inválido");
        final String text = safe(c, 4);
        final String explanation = c.length > 7 ? c[7] : "";

        require(!id.isEmpty(), "CSV: id vacío no permitido");
        require(!text.isEmpty(), "CSV: question vacía no permitida");
        require(points > 0.0, "CSV: points debe ser > 0");

        switch (type) {
            case "MCQ" -> {
                final String optionsRaw = safe(c, 5);
                final String answerRaw = safe(c, 6);
                final List<String> options = splitOptions(optionsRaw);
                final Set<Integer> correct = parseIndexes(answerRaw);

                require(!options.isEmpty(), "MCQ: options vacío");
                require(!correct.isEmpty(), "MCQ: answer vacío");
                // Validar índices dentro de rango
                for (Integer ix : correct) {
                    require(ix >= 0 && ix < options.size(),
                            "MCQ: índice de respuesta fuera de rango: " + ix);
                }

                return new McqQuestion(id, text, difficulty, points, explanation, options, correct);
            }
            case "VF" -> {
                final String answerRaw = safe(c, 6);
                require(answerRaw.equalsIgnoreCase("T") || answerRaw.equalsIgnoreCase("F"),
                        "VF: answer debe ser T o F");
                final boolean correct = answerRaw.equalsIgnoreCase("T");
                return new TrueFalseQuestion(id, text, difficulty, points, explanation, correct);
            }
            default -> throw new IllegalArgumentException("CSV: type no soportado: " + type);
        }
    }

    public static Difficulty parseDifficulty(String token) {
        final String t = trim(token).toUpperCase();
        return switch (t) {
            case "EASY" -> Difficulty.EASY;
            case "MEDIUM" -> Difficulty.MEDIUM;
            case "HARD" -> Difficulty.HARD;
            default -> throw new IllegalArgumentException("CSV: difficulty inválido: " + token);
        };
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static String safe(String[] c, int i) {
        return i < c.length ? trim(c[i]) : "";
    }

    private static void require(boolean cond, String msg) {
        if (!cond)
            throw new IllegalArgumentException(msg);
    }

    private static double parsePositiveDouble(String token, String errMsg) {
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errMsg + ": " + token);
        }
    }

    private static List<String> splitOptions(String raw) {
        if (raw.isEmpty())
            return List.of();
        String[] parts = raw.split("\\|", -1);
        List<String> list = new ArrayList<>(parts.length);
        for (String p : parts) {
            String v = trim(p);
            if (!v.isEmpty())
                list.add(v);
        }
        return List.copyOf(list);
    }

    private static Set<Integer> parseIndexes(String raw) {
        if (raw.isEmpty())
            return Set.of();
        String norm = raw.replace(',', ';');
        String[] parts = norm.split(";", -1);
        Set<Integer> set = new HashSet<>();
        for (String p : parts) {
            String v = trim(p);
            if (v.isEmpty())
                continue;
            try {
                set.add(Integer.parseInt(v));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("CSV: índice de respuesta no numérico: " + v);
            }
        }
        return Set.copyOf(set);
    }
}
