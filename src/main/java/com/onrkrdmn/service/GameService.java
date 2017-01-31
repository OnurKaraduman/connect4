package com.onrkrdmn.service;

import com.onrkrdmn.configuration.GameProperties;
import com.onrkrdmn.constant.GameState;
import com.onrkrdmn.constant.PlayerColor;
import com.onrkrdmn.domain.Game;
import com.onrkrdmn.domain.Player;
import com.onrkrdmn.repository.GameRepository;
import com.onrkrdmn.repository.PlayerRepository;
import com.onrkrdmn.repository.Repository;
import com.onrkrdmn.service.exception.GameFullException;
import com.onrkrdmn.service.exception.GameNotFoundException;
import com.onrkrdmn.service.exception.UsernameAlreadyExistException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Created by onur on 23.01.17.
 * Business layer for {@link Game}
 */
@org.springframework.stereotype.Service
@Log4j
public class GameService extends AbstractService<Game> {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameProperties gameProperties;

    @Override
    public Repository getRepository() {
        return gameRepository;
    }

    /***
     * add player to game
     * In addition, the player is added to player collection
     * @param token the unique token of the game
     * @param player player
     * @return the last game status
     */
    public Game addPlayer(String token, Player player) {
        Optional<Player> existPlayer = playerRepository.findByUserName(player.getUserName());
        if (existPlayer.isPresent()) {
            throw new UsernameAlreadyExistException("User already exist. Please use different username");
        }

        Game game = gameRepository.findByToken(token);
        if (game.getPlayers() == null) {
            throw new GameNotFoundException(token);
        } else if (game.getPlayers().size() >= 2) {
            throw new GameFullException(token);
        } else {
            log.info(player.getUserName() + " is being created for game :" + game.getToken());
            player.setGameToken(game.getToken());
            player.setColor(game.getNextPlayerColor());
            player.setOrder(game.getNextPlayerOrder());
            playerRepository.save(player);

            game.getPlayers().add(player);
            game.setState(GameState.CONTINUE);
            return gameRepository.save(game);
        }
    }

    /***
     * create new game
     * Firstly, player is inserted to database
     * Secondly, new game is created and inserted to database
     * @param player
     */
    public Game createGame(Player player) {
        Optional<Player> existPlayer = playerRepository.findByUserName(player.getUserName());
        if (existPlayer.isPresent()) {
            throw new UsernameAlreadyExistException(player.getUserName());
        }

        Game game = new Game();
        log.info("Game " + game.getToken() + " is being created with player :" + player.getUserName());
        //Set the color of player
        player.setColor(player.getColor() == null ? PlayerColor.BLACK : player.getColor());
        player.setGameToken(game.getToken());
        player.setOrder(game.getNextPlayerOrder());
        playerRepository.save(player);

        //Add player to the game
        game.getPlayers().add(player);

        //Initialize the game board
        game.initBoard(gameProperties.getBoardWidth(), gameProperties.getBoardHeight());
        return gameRepository.save(game);
    }

    /**
     * return game status instantly
     *
     * @param gameToken game id
     * @return the game status
     */
    public Game gameStatus(String gameToken) {
        return gameRepository.findByToken(gameToken);
    }

    /***
     * when the game is over,
     * delete all players and game status from table
     * Deleting operation is done permanently
     * @param gameToken game token
     * @return the last game status
     */
    public Game gameOver(String gameToken) {
        Game game = gameRepository.findByToken(gameToken);
        if (game == null) {
            throw new GameNotFoundException(gameToken);
        }
        log.info("Game " + game.getToken() + " session is being ended");
        Game gameReturn = (Game) game.clone();
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            playerRepository.delete(player);
        }
        gameReturn.setState(GameState.OVER);
        gameRepository.delete(game);
        return gameReturn;
    }

    /**
     * find the game by game token
     *
     * @param gameToken game token
     * @return
     */
    public Game findByToken(String gameToken) {
        return gameRepository.findByToken(gameToken);
    }
}
