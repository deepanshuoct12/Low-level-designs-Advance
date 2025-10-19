package org.company.dao;

import org.company.model.Option;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OptionDao implements IBaseDao<Option, String> {
    private static Map<String, Option> options = new ConcurrentHashMap<>();

    @Override
    public Option get(String id) {
        return options.get(id);
    }

    @Override
    public void save(Option option) {
        options.put(option.getId(), option);
    }

    @Override
    public void deleteById(String id) {
        options.remove(id);
    }

    @Override
    public void update(Option option) {
        options.put(option.getId(), option);
    }

    @Override
    public void delete(Option option) {
        options.remove(option.getId());
    }
}
