package com.steamnonesteam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.steamnonesteam.json.deserializers.UserGameDeserializer;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;
import com.steamnonesteam.service.Service;

import io.javalin.core.security.BasicAuthCredentials;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserGameController extends AbstractController<UserGame> {
    private final ObjectMapper objectMapper;
    private final Dao<User,Integer> userDao;
    private final Dao<UserGame,Integer> userGameDao;
    public UserGameController(Service<UserGame> service, ObjectMapper objectMapper, Dao<User,Integer> userDao,
                              Dao<UserGame,Integer> userGameDao) {
        super(service, objectMapper, UserGame.class,userDao);
        this.objectMapper = objectMapper;
        this.userDao = userDao;
        this.userGameDao = userGameDao;
    }
    public void setUserGame(Context ctx) throws JsonProcessingException, SQLException {
        BasicAuthCredentials loginPassword = ctx.basicAuthCredentials();
        String json=ctx.body();
        UserGame userGame=objectMapper.readValue(json,UserGame.class);
        User user=userDao.queryForEq("nickname",loginPassword.getUsername()).get(0);
        if(user.getId()==userGame.getUserId().getId()){
            userGameDao.create(userGame);
            ctx.result(objectMapper.writeValueAsString(userGame));
        }
        else {
            ctx.status(403);
        }
    }
}
