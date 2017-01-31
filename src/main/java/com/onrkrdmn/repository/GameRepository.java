package com.onrkrdmn.repository;


import com.onrkrdmn.domain.Game;

/**
 * Created by onur on 22.01.17.
 * The CRUD operations of {@link Game}
 */
@org.springframework.stereotype.Repository
public interface GameRepository extends Repository<Game> {

    /**
     * Find the game by token
     *
     * @param token game token
     * @return game
     */
    public Game findByToken(String token);

}
