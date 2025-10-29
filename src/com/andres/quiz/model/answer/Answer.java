package com.andres.quiz.model.answer;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public final class Answer {
    private final Set<Integer> selectedIndexes; // null si es VF
    private final Boolean trueFalse; // null si es MCQ

    private Answer(Set<Integer> selectedIndexes, Boolean trueFalse) {
        this.selectedIndexes = selectedIndexes == null ? null : Collections.unmodifiableSet(selectedIndexes);
        this.trueFalse = trueFalse;
    }

    public static Answer mcq(Set<Integer> indexes) {
        // TODO: validar no nulo; normalizar
        return new Answer(indexes, null);
    }

    public static Answer tf(boolean value) {
        return new Answer(null, value);
    }

    public boolean isMcq() {
        return trueFalse == null;
    }

    public Set<Integer> selectedIndexes() {
        return selectedIndexes == null ? Collections.emptySet() : selectedIndexes;
    }

    public Optional<Boolean> trueFalse() {
        return Optional.ofNullable(trueFalse);
    }
}
