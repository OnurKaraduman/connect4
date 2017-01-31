package com.onrkrdmn.restapi.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.onrkrdmn.restapi.dto.CreatePlayerDto;
import com.onrkrdmn.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by onur on 28.01.17.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class GameControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "onur";
    private String color = "BLACK";

    private String oAuthToken = "";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createGame() throws Exception {
        CreatePlayerDto gameModel = createGameModel("onur2", "WHITE");
        GameStatus gameRequest = createGameRequest(gameModel);
        assertNotEquals(gameRequest.gameToken, "");
    }

//    @Test
    public void getOauth2Token() throws Exception {
        CreatePlayerDto gameModel = createGameModel("onur3", "BLACK");
        GameStatus gameRequest = createGameRequest(gameModel);
        String accessToken = getAccessToken(gameModel.getUserName(), gameRequest.gameToken);
        assertNotNull(accessToken);
    }

    @Test
    public void joinGame() throws Exception {
        CreatePlayerDto gameModel = createGameModel("onur4", "BLACK");
        GameStatus gameRequest = createGameRequest(gameModel);

        CreatePlayerDto player = createGameModel("onur5", "WHITE");

        MockHttpServletResponse response = mockMvc.perform(post("/games/" + gameRequest.gameToken + "/players")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(player)))
                .andReturn().getResponse();
        GameStatus lastGameStatus = new ObjectMapper()
                .readValue(response.getContentAsByteArray(), GameStatus.class);
        assertNotEquals(lastGameStatus.gameToken, "");
    }

//    @Test
    public void gameStatus() throws Exception {
        CreatePlayerDto gameModel = createGameModel("onur6", "BLACK");
        GameStatus gameRequest = createGameRequest(gameModel);
        String accessToken = getAccessToken(gameModel.getUserName(), gameRequest.gameToken);
        assertNotEquals(accessToken, "");


        mockMvc.perform(get("/games/" + gameRequest.gameToken)
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public void authenticationControl() {
    }

    private CreatePlayerDto createGameModel() {
        CreatePlayerDto playerDto = new CreatePlayerDto();
        playerDto.setUserName(userName);
        playerDto.setColor(color);
        return playerDto;
    }

    private CreatePlayerDto createGameModel(String userName, String color) {
        CreatePlayerDto playerDto = new CreatePlayerDto();
        playerDto.setUserName(userName);
        playerDto.setColor(color);
        return playerDto;
    }

    private GameStatus createGameRequest() throws Exception {
        CreatePlayerDto playerDto = createGameModel();
        return createGameRequest(playerDto);
    }

    private GameStatus createGameRequest(CreatePlayerDto playerDto) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/games")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(playerDto)))
                .andReturn().getResponse();
        return new ObjectMapper()
                .readValue(response.getContentAsByteArray(), GameStatus.class);
    }

    private String getAccessToken(String username, String password) throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/oauth/token")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Basic "
                                + new String(Base64Utils.encode(("webui:webuisecret")
                                .getBytes())))
                        .param("password", password)
                        .param("username", username)
                        .param("grant_type", "password")
                        .param("client_secret", "webuisecret")
                        .param("client_id", "webui")
                        .param("scope", "read%20write"))
                .andReturn().getResponse();

        return new ObjectMapper()
                .readValue(response.getContentAsByteArray(), OAuthToken.class)
                .accessToken;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OAuthToken {
        @JsonProperty("access_token")
        public String accessToken;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GameStatus {
        @JsonProperty("gameToken")
        public String gameToken;

        @JsonProperty("score")
        public int score;

        @JsonProperty("state")
        public String state;
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