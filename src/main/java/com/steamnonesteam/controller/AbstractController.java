package com.steamnonesteam.controller;
import com.j256.ormlite.dao.Dao;
import com.steamnonesteam.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steamnonesteam.model.Model;
import com.steamnonesteam.service.Service;
import io.javalin.core.security.BasicAuthCredentials;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.List;
public class AbstractController<T extends Model> implements Controller<T>{
    private final Service<T> service;
    private static ObjectMapper objectMapper;
    private final Class<T> clazz;
    private final Dao<User,Integer> userDao;
    public AbstractController(Service<T> service, ObjectMapper objectMapper, Class<T> clazz,Dao<User,Integer> userDao) {
        this.service = service;
        this.objectMapper = objectMapper;
        this.clazz = clazz;
        this.userDao = userDao;
    }
    @Override
    public void getAll(Context context) {
        try {
            context.result(objectMapper.writeValueAsString(service.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500);
        }
    }
    @Override
    public void getOne(Context context, int id) throws SQLException {
        T model = service.findById(id);
        if (model == null) {
            context.status(404);
        } else {
            try {
                context.result(objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                context.status(500);
            }
        }
    }
    @Override
    public void post(Context context) {
        try {
            T model = objectMapper.readValue(context.body(), clazz);
            service.save(model);
            T saved = service.findById(model.getId());
            context.result(objectMapper.writeValueAsString(saved));
            context.status(201);
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            context.status(400);
        }
    }
    @Override
    public void patch(Context context, int id) {
        try {
            T model = objectMapper.readValue(context.body(), clazz);
            model.setId(id);
            service.update(model);
            context.status(200);
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            context.status(400);
        }

    }
    @Override
    public void delete(Context context, int id) throws SQLException {
        T model = service.findById(id);
        service.delete(model);
        context.status(204);
    }
}
