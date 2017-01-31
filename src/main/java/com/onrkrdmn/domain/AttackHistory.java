package com.onrkrdmn.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by onur on 22.01.17.
 */
@Getter
@Setter
public class AttackHistory extends AbstractEntity{
    private Player player;
    private Point position;
}
