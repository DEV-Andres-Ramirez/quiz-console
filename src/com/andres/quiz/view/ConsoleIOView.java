package com.andres.quiz.view;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.McqQuestion;
import com.andres.quiz.model.question.Question;
import com.andres.quiz.model.question.TrueFalseQuestion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public final class ConsoleIOView implements ConsoleView {

    private final Scanner in = new Scanner(System.in);

    @Override
    public void showWelcome() {
        println("");
        println("====================================");
        println("        QUIZ CONSOLE (Java)         ");
        println("====================================");
        println("Responde las preguntas según el formato indicado.");
        println("");
    }

    @Override
    public int chooseScoringStrategy() {
        println("Selecciona la estrategia de calificación:");
        println("  1) Standard       (acierto = puntos, fallo = 0)");
        println("  2) Negative       (acierto = puntos, fallo = -0.25)");
        println("  3) PartialCredit  (MCQ: crédito parcial con leve penalización)");

        while (true) {
            print("Opción [1-3]: ");
            String s = readLine();
            if (s == null)
                continue;
            s = s.trim().toLowerCase();
            if (s.equals("1") || s.equals("standard"))
                return 1;
            if (s.equals("2") || s.equals("negative"))
                return 2;
            if (s.equals("3") || s.equals("partial") || s.equals("partialcredit"))
                return 3;
            error("Opción inválida. Intenta de nuevo.");
        }
    }

    @Override
    public boolean chooseShuffle() {
        while (true) {
            print("¿Barajar preguntas? [S/N]: ");
            String s = readLine();
            if (s == null)
                continue;
            s = s.trim().toUpperCase();
            if (s.equals("S") || s.equals("SI"))
                return true;
            if (s.equals("N") || s.equals("NO"))
                return false;
            error("Entrada inválida. Escribe S o N.");
        }
    }

    @Override
    public Long chooseSeedOrNull() {
        print("Semilla para el barajado (Enter para aleatoria): ");
        String s = readLine();
        if (s == null)
            return null;
        s = s.trim();
        if (s.isEmpty())
            return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ex) {
            error("Semilla inválida. Se usará aleatoria.");
            return null;
        }
    }

    @Override
    public void showPrompt(String promptText) {
        println("");
        print(promptText);
    }

    @Override
    public Answer readAnswerFor(Question q) {
        if (q instanceof TrueFalseQuestion) {
            return readAnswerTrueFalse();
        } else if (q instanceof McqQuestion mcq) {
            return readAnswerMcq(mcq);
        } else {
            throw new IllegalArgumentException("Tipo de pregunta no soportado: " + q.getClass().getSimpleName());
        }
    }

    @Override
    public void showPerQuestionFeedback(Question q, Answer a, double obtained, double max, boolean correct,
            String explanation) {
        String status = correct ? "✔ Correcto" : "✘ Incorrecto";
        println("");
        println("Resultado: " + status + " | Puntaje: " + formatDouble(obtained) + " / " + formatDouble(max));
        if (explanation != null && !explanation.isBlank()) {
            println("Explicación: " + explanation);
        }
    }

    @Override
    public void showSummary(double totalObtained, double totalMax, int correctCount, int wrongCount) {
        println("");
        println("====================================");
        println("               RESUMEN              ");
        println("====================================");
        println("Puntaje total: " + formatDouble(totalObtained) + " / " + formatDouble(totalMax));
        int total = correctCount + wrongCount;
        double pct = total == 0 ? 0.0 : ((double) correctCount / (double) total) * 100.0;
        println("Aciertos: " + correctCount + " | Fallos: " + wrongCount + " | Precisión: "
                + String.format("%.1f%%", pct));
        println("====================================");
    }

    @Override
    public void info(String message) {
        println("[INFO] " + message);
    }

    @Override
    public void error(String message) {
        println("[ERROR] " + message);
    }

    private Answer readAnswerTrueFalse() {
        while (true) {
            print("Tu respuesta (T/F): ");
            String s = readLine();
            if (s == null)
                continue;
            s = s.trim().toUpperCase();
            if (s.equals("T") || s.equals("TRUE"))
                return Answer.tf(true);
            if (s.equals("F") || s.equals("FALSE"))
                return Answer.tf(false);
            error("Entrada inválida. Usa T (true) o F (false).");
        }
    }

    private Answer readAnswerMcq(McqQuestion mcq) {
        final int maxIndex = mcq.optionCount() - 1;

        while (true) {
            print("Tu respuesta (ej: 1 o 0,2): ");
            String s = readLine();
            if (s == null)
                continue;

            Set<Integer> indexes = parseIndexSet(s);
            if (indexes.isEmpty()) {
                error("Debes seleccionar al menos un índice.");
                continue;
            }
            boolean outOfRange = indexes.stream().anyMatch(ix -> ix < 0 || ix > maxIndex);
            if (outOfRange) {
                error("Índice fuera de rango. Válidos: 0.." + maxIndex);
                continue;
            }
            return Answer.mcq(indexes);
        }
    }

    private Set<Integer> parseIndexSet(String raw) {
        String norm = (raw == null) ? "" : raw.trim();
        if (norm.isEmpty())
            return Set.of();
        norm = norm.replace('，', ',');
        norm = norm.replace(';', ',');

        String[] parts = Arrays.stream(norm.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        Set<Integer> out = new HashSet<>();
        for (String p : parts) {
            try {
                out.add(Integer.parseInt(p));
            } catch (NumberFormatException ex) {
                out.clear();
                break;
            }
        }
        return out;
    }

    private String formatDouble(double d) {
        return String.format("%.2f", d);
    }

    private void print(String s) {
        System.out.print(s);
    }

    private void println(String s) {
        System.out.println(s);
    }

    private String readLine() {
        try {
            return in.nextLine();
        } catch (Exception e) {
            error("Error leyendo entrada. Intenta de nuevo.");
            return null;
        }
    }
}
