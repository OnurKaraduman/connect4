package com.onrkrdmn.repository;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.onrkrdmn.constant.PlayerColor;
import com.onrkrdmn.domain.Game;
import com.onrkrdmn.domain.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by onur on 29.01.17.
 * using {@link Fongo} for mongodb testing
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void save() {
        Game game = new Game();
        Player player = createPlayer();
        player.setUserName("onur2");
        game.getPlayers().add(player);
        Game savedGame = gameRepository.save(game);
        assertNotEquals(savedGame.getId(), "");
    }

    @Test
    public void findAll() {
        createAndInsertGame();
        List<Game> allGame = gameRepository.findAll();
        assertTrue(allGame.size() > 0);
    }

    @Test
    public void findOne() {
        createAndInsertGame();
        Game gameTmp = gameRepository.findOne("0987654321");
        assertNotNull(gameTmp);
    }

    @Test
    public void delete() {
        createAndInsertGame();
        Game gameTmp = gameRepository.findOne("0987654321");
        gameRepository.delete(gameTmp);
        gameTmp = gameRepository.findOne("0987654321");
        assertNull(gameTmp);
    }

    @Test
    public void findByToken() throws Exception {
        createAndInsertGame();
        Game gameTmp = gameRepository.findByToken("1234567890");
        assertNotNull(gameTmp);
    }

    private Player createPlayer() {
        Player player = new Player();
        player.setUserName("onur");
        player.setColor(PlayerColor.WHITE);
        return player;
    }

    private Game createGame() {
        Game game = new Game();
        game.setToken("1234567890");
        game.setId("0987654321");
        Player player = createPlayer();
        game.getPlayers().add(player);
        return game;
    }

    private Game createAndInsertGame() {
        Game game = createGame();
        return gameRepository.save(game);
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {GameRepository.class})
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