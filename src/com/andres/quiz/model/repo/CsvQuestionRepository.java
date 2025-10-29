package com.andres.quiz.model.repo;

import com.andres.quiz.model.factory.QuestionFactory;
import com.andres.quiz.model.question.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CsvQuestionRepository implements QuestionRepository {

    private final String csvPath;

    public CsvQuestionRepository(String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public List<Question> loadAll() {
        List<Question> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                if (line.isBlank() || line.stripLeading().startsWith("#"))
                    continue;

                String[] cols = parseCsvLine(line);
                try {
                    Question q = QuestionFactory.fromCsvRow(cols);
                    out.add(q);
                } catch (IllegalArgumentException ex) {
                    System.err.println("[CSV] Fila omitida: " + ex.getMessage() + " | " + line);
                }
            }
        } catch (IOException io) {
            System.err.println("[CSV] Error leyendo archivo: " + csvPath + " -> " + io.getMessage());
        }
        return List.copyOf(out);
    }

    private static String[] parseCsvLine(String line) {
        List<String> cols = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (inQuotes) {
                if (ch == '"') {
                    boolean hasNext = (i + 1) < line.length();
                    if (hasNext && line.charAt(i + 1) == '"') {
                        cur.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(ch);
                }
            } else {
                if (ch == ',') {
                    cols.add(cur.toString().trim());
                    cur.setLength(0);
                } else if (ch == '"') {
                    inQuotes = true;
                } else {
                    cur.append(ch);
                }
            }
        }
        cols.add(cur.toString().trim());
        return cols.toArray(new String[0]);
    }
}
