package com.onrkrdmn.domain;

import com.onrkrdmn.constant.PlayerColor;
import com.onrkrdmn.constant.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import java.util.Date;

/**
 * Created by onur on 21.01.17.
 * The player who is able to create and join the game
 */
@Getter
@Setter
public class Player extends AbstractEntity {

    private String name;

    @Mapping("color")
    private PlayerColor color;

    @Mapping("userName")
    private String userName;

    private String gameToken;

    private int order;

    private String[] roles = {RoleType.USER.toString()};
}
