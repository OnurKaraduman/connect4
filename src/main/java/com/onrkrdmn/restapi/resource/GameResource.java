package com.onrkrdmn.restapi.resource;

import com.onrkrdmn.constant.GameState;
import com.onrkrdmn.constant.PlayerColor;
import com.onrkrdmn.domain.Game;
import com.onrkrdmn.domain.Player;
import com.onrkrdmn.restapi.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapper;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by onur on 28.01.17.
 * Game resource
 */
@Getter
@Setter
public class GameResource extends ResourceSupport {

    private BoardDto board;

    private int score;

    private String gameToken;

    private PlayerColor playerColor;

    private GameState state;

    public GameResource(Game game, Player player, Mapper mapper) {
        if (game.getBoard() != null) {
            this.board = mapper.map(game.getBoard(), BoardDto.class);
        }
        this.score = game.getScore();
        this.gameToken = game.getToken();
        this.playerColor = player.getColor();
        this.state = game.getState();
    }

    public GameResource(Game game, Mapper mapper) {
        if (game.getBoard() != null) {
            this.board = mapper.map(game.getBoard(), BoardDto.class);
        }
        if(game.getPlayers()!=null){
            this.playerColor = game.getPlayers().get(0).getColor();
        }
        this.score = game.getScore();
        this.gameToken = game.getToken();
        this.state = game.getState();
    }
}
