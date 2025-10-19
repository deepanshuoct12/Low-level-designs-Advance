package org.company.service;

import org.company.dao.OptionDao;
import org.company.model.Option;

public class OptionService {
    private final OptionDao optionDao;

    public OptionService() {
        optionDao = new OptionDao();
    }

    public Option createOption(String questionId, Integer optionValue, String optionText) {
        Option option = new Option();
        option.setQuestionId(questionId);
        option.setOption(optionValue);
        option.setOptionText(optionText);
        optionDao.save(option);
        return option;
    }

    public Option getOption(String optionId) {
        return optionDao.get(optionId);
    }

    public void updateOption(Option option) {
        optionDao.update(option);
    }

    public void deleteOption(String optionId) {
        optionDao.deleteById(optionId);
    }

    public void updateOptionValue(String optionId, Integer newOptionValue, String optionText) {
        Option option = optionDao.get(optionId);
        if (option != null) {
            option.setOption(newOptionValue);
            option.setOptionText(optionText);
            optionDao.update(option);
        }
    }
}
