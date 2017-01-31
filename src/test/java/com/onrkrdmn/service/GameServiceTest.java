package com.onrkrdmn.service;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.onrkrdmn.constant.GameState;
import com.onrkrdmn.constant.PlayerColor;
import com.onrkrdmn.domain.Game;
import com.onrkrdmn.domain.Player;
import com.onrkrdmn.repository.GameRepository;
import com.onrkrdmn.service.exception.GameFullException;
import com.onrkrdmn.service.exception.UsernameAlreadyExistException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by onur on 28.01.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void getRepository() throws Exception {
        assertTrue(gameService.getRepository() != null);
    }

    @Test(expected = UsernameAlreadyExistException.class)
    public void userNameAlreadyExist() {
        Game game = createAndInsertGame();
        Player player = createPlayer();
        gameService.addPlayer("1234567890", player);
    }

    @Test(expected = GameFullException.class)
    public void isGameFull() {
        Game game = createAndInsertGame();
        if (game.getPlayers().size() < 2) {
            Player player = createPlayer();
            player.setUserName("onur4");
            gameService.addPlayer("1234567890", player);

            Player player2 = createPlayer();
            player2.setUserName("onur5");
            gameService.addPlayer("1234567890", player2);
        } else if (game.getPlayers().size() == 2) {
            Player player3 = createPlayer();
            player3.setUserName("onur6");
            gameService.addPlayer("1234567890", player3);
        }
    }

    @Test
    public void addPlayer() throws Exception {
        Player player = createPlayer();
        player.setUserName("onur2");
        Game game = gameService.addPlayer("1234567890", player);
        assertTrue(game.getPlayers().size() == 2);
    }

    @Test
    public void createGame() throws Exception {
        Player player = createPlayer();
        player.setUserName("onur3");
        Game savedGame = gameService.createGame(player);
        assertNotEquals(savedGame.getId(), "");
    }

    @Test
    public void gameStatus() throws Exception {
        createAndInsertGame();
        Game game = gameService.gameStatus("1234567890");
        assertNotNull(game);
    }

    @Test
    public void gameOver() throws Exception {
        createAndInsertGame();
        Game game = gameService.gameOver("1234567890");
        assertNotNull(game);
        assertEquals(game.getState(), GameState.OVER);
    }

    private Player createPlayer() {
        Player player = new Player();
        player.setUserName("onur");
        player.setColor(PlayerColor.WHITE);
        return player;
    }

    private Game createGameModel() {
        Game game = new Game();
        game.setToken("1234567890");
        game.setId("0987654321");
        Player player = createPlayer();
        player.setGameToken(game.getToken());
        game.getPlayers().add(player);
        return game;
    }

    private Game createAndInsertGame() {
        Game existGame = gameService.findById("0987654321");
        if (existGame == null) {
            Game gameModel = createGameModel();
            playerService.save(gameModel.getPlayers().get(0));
            return gameService.save(gameModel);
        }
        return existGame;
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackages = "com.onrkrdmn")
    static class MongoConfiguration extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "connect4-db";
        }

        @Override
        public Mongo mongo() {
            return new Fongo("connect4j-test").getMongo();
        }

        @Override
        protected String getMappingBasePackage() {
            return "com.onrkrdmn";
        }
    }
}