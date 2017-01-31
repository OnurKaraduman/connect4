package com.onrkrdmn.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by onur on 31.01.17.
 * Constant properties for the game
 */
@Component
@ConfigurationProperties(prefix = "game")
@Getter
@Setter
public class GameProperties {
    private int boardWidth;
    private int boardHeight;
}
