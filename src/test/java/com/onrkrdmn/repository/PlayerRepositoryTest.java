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
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by onur on 29.01.17.
 * using {@link Fongo} for mongodb testing
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    public void save() {
        Player player = createPlayer();
        player.setUserName("onur2");
        Player player1 = playerRepository.save(player);
        assertNotEquals(player1.getId(), "");
    }

    @Test
    public void findAll() {
        createAndInsertPlayer();
        List<Player> allPlayer = playerRepository.findAll();
        assertTrue(allPlayer.size() > 0);
    }

    @Test
    public void findOne() {
        Player player = createAndInsertPlayer();
        Player playerTmp = playerRepository.findOne("0987654321");
        assertNotNull(playerTmp);
    }


    @Test
    public void findByGameToken() throws Exception {
        createAndInsertPlayer();
        Player player = playerRepository.findOne("0987654321");
        assertNotNull(player);
    }

    @Test
    public void findByUserName() throws Exception {
        createAndInsertPlayer();
        Optional<Player> player = playerRepository.findByUserName("onur");
        assertTrue(player.isPresent());
    }

    private Player createPlayer() {
        Player player = new Player();
        player.setUserName("onur");
        player.setColor(PlayerColor.BLACK);
        player.setGameToken("1234567890");
        player.setId("0987654321");
        return player;
    }

    private Player createAndInsertPlayer() {
        Player player = createPlayer();
        Optional<Player> playerTmp = playerRepository.findByUserName(player.getUserName());
        if (!playerTmp.isPresent()) {
            return playerRepository.save(player);
        }
        return player;
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {PlayerRepository.class})
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