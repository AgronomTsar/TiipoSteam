package com.steamnonesteam;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.steamnonesteam.authorization.Authorization;
import com.steamnonesteam.authorization.Roles;
import com.steamnonesteam.configuration.DatabaseConfiguration;
import com.steamnonesteam.configuration.JdbcDatabaseConfiguration;
import com.steamnonesteam.controller.*;
import com.steamnonesteam.json.deserializers.GameDeserializer;
import com.steamnonesteam.json.deserializers.UserDeserializer;
import com.steamnonesteam.json.deserializers.UserGameDeserializer;
import com.steamnonesteam.json.serializers.GameSerializer;
import com.steamnonesteam.json.serializers.UserGameSerializer;
import com.steamnonesteam.json.serializers.UserSerializer;
import com.steamnonesteam.model.Game;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;
import com.steamnonesteam.service.*;
import io.javalin.Javalin;
import java.sql.SQLException;
import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConfiguration configuration = new JdbcDatabaseConfiguration("jdbc:sqlite:C:\\Users\\77012\\Desktop\\бд\\steam.db");
        Dao<User,Integer> userDao= DaoManager.createDao(configuration.connectionSource(),User.class);
        Dao<Game,Integer> gameDao= DaoManager.createDao(configuration.connectionSource(),Game.class);
        Dao<UserGame,Integer> userGameDao= DaoManager.createDao(configuration.connectionSource(),UserGame.class);
        User user=new User();
        Authorization authorization=new Authorization(userDao,userGameDao);
        SimpleModule simpleModule = new SimpleModule()
                .addSerializer(new UserSerializer())
                .addSerializer(new GameSerializer())
                .addSerializer(new UserGameSerializer())
                .addDeserializer(User.class, new UserDeserializer())
                .addDeserializer(Game.class, new GameDeserializer())
                .addDeserializer(UserGame.class,new UserGameDeserializer(userDao,gameDao));
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(simpleModule);
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.prefer405over404 = true;
        });
        Service<User> userService = new UserService(userDao);
        LibraryService ls=new LibraryService(userGameDao);
        GameService gameService=new GameService(gameDao);
        UserGameService userGameService=new UserGameService(userGameDao);
        Controller<User> UserController = new UserController(userService,objectMapper,userDao);
        GameController gameController = new GameController(gameService,objectMapper,userDao,userGameDao,gameDao,user);
        UserGameController UserGameController = new UserGameController(userGameService,objectMapper,userDao,userGameDao);
        LibraryController lc=new LibraryController(app,ls);

        app.config.accessManager((handler,context,permittedRoles)->{
            Roles userRole = authorization.getUserRole(context);
            if (permittedRoles.contains(userRole)) {
                handler.handle(context);
            } else {
                context.status(401).result("Unauthorized");
            }
        });
        app.routes(() -> {
            path("users", () -> {
                get(UserController::getAll,roles(Roles.ADMIN));
                post(UserController::post,roles(Roles.USER,Roles.ADMIN));
                path(":id", () -> {
                    get(ctx -> UserController.getOne(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN,Roles.USER));
                    patch(ctx -> UserController.patch(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN,Roles.USER));
                    delete(ctx -> UserController.delete(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.USER,Roles.ADMIN));
                });
            });
            path("games", () -> {
                get(gameController::getAll,roles(Roles.ADMIN));
                post(gameController::post,roles(Roles.ADMIN));
                path(":id", () -> {
                    get(ctx -> gameController.getGameById(ctx.pathParam("id", Integer.class).get(),ctx),roles(Roles.ADMIN,Roles.USER));
                    patch(ctx -> gameController.patch(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN));
                    delete(ctx -> gameController.delete(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN));
                });
            });
            path("userGame", () -> {
                get(UserGameController::getAll,roles(Roles.ADMIN));
                post(UserGameController::post,roles(Roles.ADMIN));
                path(":id", () -> {
                    get(ctx -> UserGameController.getOne(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN));
                    patch(ctx -> UserGameController.patch(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN));
                    delete(ctx -> UserGameController.delete(ctx, ctx.pathParam("id", Integer.class).get()),roles(Roles.ADMIN));
                });
            });
            path("Library",()->{
                path(":user",()->{
                    get(ctx -> ctx.result(ls.findLibraryByUser(Integer.parseInt(ctx.pathParam("user")))),roles(Roles.ADMIN,Roles.USER));
                    post(ctx -> UserGameController.setUserGame(ctx),roles(Roles.ADMIN,Roles.USER));
                    delete(ctx -> ls.deleteLibraryByUserIdGameId(Integer.parseInt(ctx.pathParam("user")),
                            Integer.parseInt(ctx.pathParam("game"))),roles(Roles.ADMIN,Roles.USER));
                });
            });
        });
        app.start(8080);
    }
}
