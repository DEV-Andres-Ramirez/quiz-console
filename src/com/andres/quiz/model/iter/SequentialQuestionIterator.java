package com.andres.quiz.model.iter;

import com.andres.quiz.model.question.Question;

import java.util.List;
import java.util.NoSuchElementException;

public final class SequentialQuestionIterator implements QuestionIterator {

    private final List<Question> questions;
    private int index = 0;

    public SequentialQuestionIterator(List<Question> questions) {
        if (questions == null)
            throw new IllegalArgumentException("questions == null");
        this.questions = List.copyOf(questions);
    }

    @Override
    public boolean hasNext() {
        return index < questions.size();
    }

    @Override
    public Question next() {
        if (!hasNext())
            throw new NoSuchElementException("No more questions");
        return questions.get(index++);
    }
}
