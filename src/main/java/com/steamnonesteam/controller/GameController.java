package com.steamnonesteam.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.steamnonesteam.model.Game;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;
import com.steamnonesteam.service.Service;
import io.javalin.core.security.BasicAuthCredentials;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import javax.management.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class GameController extends AbstractController<Game> {
    private final Dao<User,Integer> userDao;
    private final Dao<UserGame,Integer> userGameDao;
    private final Dao<Game,Integer> gameDao;
    private final ObjectMapper objectMapper;
    private User user1;
    public GameController(Service<Game> service, ObjectMapper objectMapper, Dao<User,Integer> userDao,Dao<UserGame,Integer> userGameDao,
                          Dao<Game,Integer> gameDao,User user) {
        super(service, objectMapper,Game.class,userDao);
        this.userGameDao=userGameDao;
        this.gameDao=gameDao;
        this.objectMapper=objectMapper;
        this.userDao=userDao;
        this.user1=user;
    }
    public void getGameById(int id,Context ctx) throws SQLException, JsonProcessingException {
        BasicAuthCredentials loginPassword = ctx.basicAuthCredentials();
        User user=userDao.queryForEq("nickname",loginPassword.getUsername()).get(0);
        if(BCrypt.checkpw(loginPassword.getPassword(), user.getPassword())) {
            UserGame userGame = userGameDao.queryForEq("userid", user.getId()).get(0);
//          QueryBuilder<UserGame, Integer> qb = userGameDao.queryBuilder();
//          UserGame userGame = qb.where().eq("userid", user.getId()).query().get(0);
           if (userGame.getGameId().getId()==id){
                Game game = gameDao.queryForId(id);
                ctx.result(objectMapper.writeValueAsString(game));
            }
      }
        else {
            ctx.status(403);
        }
    }

}
