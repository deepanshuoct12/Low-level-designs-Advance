package org.company.stratergy;

import org.company.enums.QuestionType;

import java.util.HashMap;

public interface IQuestionStratergy {
    Boolean isApplicable(QuestionType questionType);
    Integer computeScore(String questionId, String optionId);
}
