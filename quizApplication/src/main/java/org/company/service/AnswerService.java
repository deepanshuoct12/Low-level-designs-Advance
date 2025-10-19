package org.company.service;

import org.company.dao.AnswerDao;
import org.company.model.Answer;

public class AnswerService {
    private final AnswerDao answerDao;

    public AnswerService() {
        answerDao = new AnswerDao();
    }

    public Answer createAnswer(String questionId, String optionId) {
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setOptionId(optionId);
        answerDao.save(answer);
        return answer;
    }

    public Answer getAnswer(String answerId) {
        return answerDao.get(answerId);
    }


    public void updateAnswer(Answer answer) {
        answerDao.update(answer);
    }

    public void deleteAnswer(String answerId) {
        answerDao.deleteById(answerId);
    }

    public void updateOptionId(String answerId, String newOptionId) {
        Answer answer = answerDao.get(answerId);
        if (answer != null) {
            answer.setOptionId(newOptionId);
            answerDao.update(answer);
        }
    }

    public Answer getAnswerByQuestionId(String questionId) {
        return answerDao.getAnswerByQuestionId(questionId);
    }
}
