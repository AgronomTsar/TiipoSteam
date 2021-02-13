package com.steamnonesteam.json.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.j256.ormlite.dao.Dao;
import com.steamnonesteam.model.Game;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;

import java.io.IOException;
import java.sql.SQLException;

public class UserGameDeserializer extends StdDeserializer<UserGame> {
    private final Dao<User,Integer> daoUser;
    private final Dao<Game,Integer> daoGame;
    public UserGameDeserializer(Dao<User, Integer> dao, Dao<Game, Integer> daoGame) {
        super(UserGame.class);
        daoUser = dao;
        this.daoGame = daoGame;
    }
    @Override
    public UserGame deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("id").asInt();
        int userId=root.get("userId").asInt();
        int gameId=root.get("gameId").asInt();
        try {
            return  new UserGame(id,daoUser.queryForId(userId),daoGame.queryForId(gameId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
