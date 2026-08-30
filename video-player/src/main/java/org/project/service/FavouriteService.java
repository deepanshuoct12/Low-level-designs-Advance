package org.project.service;

import org.project.model.Favourite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FavouriteService {
    private static final Map<Long, Favourite> favouriteStore = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public Favourite create(Favourite favourite) {
        favourite.setId(idGenerator.getAndIncrement());
        favourite.onCreate();
        favouriteStore.put(favourite.getId(), favourite);
        return favourite;
    }

    public Favourite getById(Long id) {
        return favouriteStore.get(id);
    }

    public List<Favourite> getAll() {
        return favouriteStore.values().stream().collect(Collectors.toList());
    }

    public Favourite update(Long id, Favourite favourite) {
        if (favouriteStore.containsKey(id)) {
            favourite.setId(id);
            favourite.onUpdate();
            favouriteStore.put(id, favourite);
            return favourite;
        }
        return null;
    }

    public boolean delete(Long id) {
        return favouriteStore.remove(id) != null;
    }
}
