package com.steamnonesteam.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Game")
public class Game implements Model{
    @DatabaseField(id=true)
    int gameId;
    @DatabaseField
    String name;
    @DatabaseField
    String description;
    @DatabaseField
    String price;
    public Game(){
    }
    public Game(int gameId, String name, String description, String price) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public int getId() {
        return gameId;
    }

    @Override
    public void setId(int id) {
        this.gameId=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
