package com.steamnonesteam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.steamnonesteam.model.User;
import com.steamnonesteam.service.Service;

public class UserController extends AbstractController<User> {
    public UserController(Service<User> service, ObjectMapper objectMapper, Dao<User,Integer> userDao) {
        super(service, objectMapper, User.class,userDao);
    }
}
