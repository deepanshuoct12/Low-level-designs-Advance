package org.dynamik.model;

import java.util.HashMap;
import java.util.List;

public class Board {
    private Long size;
    HashMap<Long, Long> snakesAndLadder;

    public Board(Long size, List<BoardEntity> boardEntites) {
        this.size = size;
        this.snakesAndLadder = new HashMap<>();

        for(BoardEntity boardEntity : boardEntites) {
            snakesAndLadder.put(boardEntity.getStart(), boardEntity.getEnd());
        }
    }

    public Long getSize() {
        return size;
    }

    public Long getFinalPosition(Long pos) {
        return snakesAndLadder.getOrDefault(pos, pos);
    }
}
