package com.onrkrdmn.repository;


import com.onrkrdmn.domain.Player;

import java.util.Optional;

/**
 * Created by onur on 27.01.17.
 * The CRUD operations of {@link Player}
 */
@org.springframework.stereotype.Repository
public interface PlayerRepository extends Repository<Player> {

    /***
     * find the player by token id
     * @return player
     */
    public Player findByGameToken(String gameToken);

    /***
     * find the player by user name
     * @param userName
     * @return
     */
    public Optional<Player> findByUserName(String userName);
}
