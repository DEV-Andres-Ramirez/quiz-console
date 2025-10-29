package com.andres.quiz.model.iter;

import com.andres.quiz.model.question.Question;

public interface QuestionIterator {
    
    boolean hasNext();

    Question next();
}
