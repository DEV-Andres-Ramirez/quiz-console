package com.andres.quiz.model.obs;

import com.andres.quiz.model.answer.Answer;
import com.andres.quiz.model.question.Question;

public interface QuizObserver {

    void onQuestionAnswered(Question q, Answer a, double score);
}
