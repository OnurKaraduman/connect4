package com.onrkrdmn.service;

import com.onrkrdmn.domain.Player;
import com.onrkrdmn.repository.PlayerRepository;
import com.onrkrdmn.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

/**
 * Created by onur on 27.01.17.
 * Business layer for {@link Player}
 */
@org.springframework.stereotype.Service
public class PlayerService extends AbstractService<Player> {

    @Autowired
    @Qualifier("playerRepository")
    private PlayerRepository repository;

    @Override
    public Repository getRepository() {
        return this.repository;
    }

    public Player findByToken(String token) {
        return this.repository.findByGameToken(token);
    }

    public Optional<Player> findByUserName(String userName) {
        return this.repository.findByUserName(userName);
    }
}
