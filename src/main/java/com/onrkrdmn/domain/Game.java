package com.onrkrdmn.domain;


import com.onrkrdmn.constant.GameState;
import com.onrkrdmn.constant.PlayerColor;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.onrkrdmn.constant.PlayerColor.*;

/**
 * Created by onur on 21.01.17.
 * indicates all properties of game
 */
@Getter
@Setter
public class Game extends AbstractEntity {

    private List<Player> players = new ArrayList<>();

    @Mapping("board")
    private Board board;

    private AttackHistory attackHistory;

    @Mapping("score")
    private int score = 0;

    private String token;

    private GameState state = GameState.WAITING;

    public Game() {
        this.token = UUID.randomUUID().toString();
    }

    @Transient
    public void initBoard(int width, int height) {
        this.board = new Board(width, height);
    }

    @Transient
    public void addPlayer(Player player) {
        player.setColor(getNextPlayerColor());
        player.setOrder(getNextPlayerOrder());
        getPlayers().add(player);
    }

    @Transient
    public PlayerColor getNextPlayerColor() {
        if (getPlayers().size() == 1) {
            Player player1 = getPlayers().get(0);
            switch (player1.getColor()) {
                case BLACK:
                    return WHITE;
                case WHITE:
                    return BLACK;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    @Transient
    public int getNextPlayerOrder() {
        if (getPlayers() == null) {
            return -1;
        } else if (getPlayers().size() == 0) {
            return 1;
        } else if (getPlayers().size() == 1) {
            return 2;
        } else {
            return -1;
        }
    }
}
