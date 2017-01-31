package com.onrkrdmn.domain;

import com.onrkrdmn.constant.PlayerColor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dozer.Mapping;

import java.util.Map;

/**
 * Created by onur on 22.01.17.
 * represent the matrix board status in the game
 * The board is created as matrix table with value which comes from configuration file
 * Maybe in the future, matrix size can be set by player.
 */
@Getter
@Setter
@NoArgsConstructor
public class Board extends AbstractEntity {
    @Mapping("points")
    private Map<Point, PlayerColor> points;

    private int width;

    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
