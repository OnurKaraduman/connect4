package com.onrkrdmn.restapi.controller;

import com.onrkrdmn.domain.Game;
import com.onrkrdmn.domain.Player;
import com.onrkrdmn.restapi.dto.CreateGameDto;
import com.onrkrdmn.restapi.dto.CreatePlayerDto;
import com.onrkrdmn.restapi.model.Response;
import com.onrkrdmn.restapi.resource.GameResource;
import com.onrkrdmn.service.GameService;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by onur on 22.01.17.
 * rest service controller for the game
 */
@RestController
@RequestMapping("/games")
public class GameController implements Controller {

    @Autowired
    private GameService gameService;

    private Mapper mapper;

    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response startGame(@Validated @RequestBody CreateGameDto gameDto) {
        Player playerDomain = mapper.map(gameDto, Player.class);
        Game game = gameService.createGame(playerDomain);
        GameResource gameResource = new GameResource(game, mapper);
        return new Response(gameResource, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/{gameToken}")
    public Response gameStatus(@Validated @PathVariable String gameToken) {
        Game game = gameService.gameStatus(gameToken);
        GameResource gameResource = new GameResource(game, mapper);
        return new Response(gameResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{token}/players", method = RequestMethod.POST)
    public Response joinGame(@Validated @PathVariable String token, @Validated @RequestBody CreatePlayerDto player) {
        Player playerDomain = mapper.map(player, Player.class);
        Game game = gameService.addPlayer(token, playerDomain);
        GameResource gameResource = new GameResource(game, playerDomain, mapper);
        return new Response(gameResource, HttpStatus.OK);
    }
}
