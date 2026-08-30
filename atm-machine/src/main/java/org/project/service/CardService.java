package org.project.service;

import org.project.model.Card;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CardService {
    private static final Map<Long, Card> cardStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Card create(Card card) {
        card.setId(idCounter++);
        cardStore.put(card.getId(), card);
        return card;
    }

    public Card getById(Long id) {
        return cardStore.get(id);
    }

    public List<Card> getAll() {
        return cardStore.values().stream().collect(Collectors.toList());
    }

    public Card update(Long id, Card card) {
        if (cardStore.containsKey(id)) {
            card.setId(id);
            cardStore.put(id, card);
            return card;
        }
        return null;
    }

    public boolean delete(Long id) {
        return cardStore.remove(id) != null;
    }
}
