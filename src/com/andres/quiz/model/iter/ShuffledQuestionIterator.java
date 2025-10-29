package com.andres.quiz.model.iter;

import com.andres.quiz.model.question.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public final class ShuffledQuestionIterator implements QuestionIterator {

    private final List<Question> shuffled;
    private int index = 0;

    public ShuffledQuestionIterator(List<Question> questions, long seed) {
        if (questions == null)
            throw new IllegalArgumentException("questions == null");
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy, new Random(seed));
        this.shuffled = List.copyOf(copy);
    }

    @Override
    public boolean hasNext() {
        return index < shuffled.size();
    }

    @Override
    public Question next() {
        if (!hasNext())
            throw new NoSuchElementException("No more questions");
        return shuffled.get(index++);
    }
}
