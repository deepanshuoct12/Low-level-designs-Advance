package org.dynamik.model;

import lombok.Data;

@Data
public class Player {
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
