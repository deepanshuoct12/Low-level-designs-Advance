package org.company.stratergy;

import org.company.enums.QuestionType;
import org.company.model.Answer;
import org.company.service.AnswerService;

public class TrueFalseStratergy implements IQuestionStratergy {

    private AnswerService answerService = new AnswerService();

    @Override
    public Boolean isApplicable(QuestionType questionType) {
        return questionType.equals(QuestionType.TRUE_FALSE);
    }

    @Override
    public Integer computeScore(String questionId, String optionId) {
        Answer answer = answerService.getAnswerByQuestionId(questionId);
        if (answer != null && answer.getOptionId().equals(optionId)) {
            return 1;
        }

        return 0;
    }
}
