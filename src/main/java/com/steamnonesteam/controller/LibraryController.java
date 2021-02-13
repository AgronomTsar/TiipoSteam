package com.steamnonesteam.controller;
import com.steamnonesteam.service.LibraryService;
import io.javalin.Javalin;
public class LibraryController {
    Javalin app;
    LibraryService libraryService;
    public LibraryController(Javalin app, LibraryService libraryService){
        this.app=app;
        this.libraryService=libraryService;
    }
    public void getLibraryById(){
        app.get("/Library/:user",ctx->{
            ctx.result(libraryService.findLibraryByUser(Integer.parseInt(ctx.pathParam("user"))));
            ctx.status(200);
        });
    }
    public void deleteLibrary(){
        app.delete("/Library/:user/:game",ctx->{
            libraryService.deleteLibraryByUserIdGameId(Integer.parseInt(ctx.pathParam("user")),
                    Integer.parseInt(ctx.pathParam("game")));
            ctx.status(204);
        });
    }
}