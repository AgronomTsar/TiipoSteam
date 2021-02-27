package com.steamnonesteam.controller;

import com.steamnonesteam.model.Model;
import io.javalin.http.Context;

import java.sql.SQLException;

public interface Controller<T> {
    void getAll(Context context,int amount);
    void getOne(Context context, int id) throws SQLException;
    void post(Context context);
    void patch(Context context, int id);
    void delete(Context context, int id) throws SQLException;
}
