package com.steamnonesteam.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "User_Game")
public class UserGame implements Model{
    @DatabaseField(columnName = "UserGameId",id=true)
    int userGameId;
    @DatabaseField(foreign=true, foreignAutoRefresh = true, columnName = "userid")
    User userId;
    @DatabaseField(foreign=true, foreignAutoRefresh = true, columnName = "gameid")
    Game gameId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGame userGame = (UserGame) o;
        return userGameId == userGame.userGameId &&
                Objects.equals(userId, userGame.userId) &&
                Objects.equals(gameId, userGame.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGameId, userId, gameId);
    }

    public UserGame(){
    }

    public UserGame(int userGameId, User userId, Game gameId) {
        this.userGameId = userGameId;
        this.userId = userId;
        this.gameId = gameId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    @Override
    public int getId() {
        return userGameId;
    }
    @Override
    public void setId(int id) {
        userGameId=id;
    }

}
