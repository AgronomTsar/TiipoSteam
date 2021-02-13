package com.steamnonesteam.authorization;
import com.j256.ormlite.dao.Dao;
import com.steamnonesteam.model.User;
import com.steamnonesteam.model.UserGame;
import io.javalin.core.security.BasicAuthCredentials;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;

public class Authorization{
    private Dao<User, Integer> dao;
    private Dao<UserGame,Integer> libraryDao;
    public Authorization(Dao<User, Integer> dao,Dao<UserGame,Integer> libraryDao)
    {
        this.dao = dao;
        this.libraryDao=libraryDao;
    }
    public Roles getUserRole(Context ctx) throws SQLException {
        if(ctx.basicAuthCredentials().getUsername().equals("")){
            return Roles.USER;
        }
        BasicAuthCredentials loginPassword=ctx.basicAuthCredentials();
        User user=dao.queryForEq("nickname",loginPassword.getUsername()).get(0);
        if(BCrypt.checkpw(loginPassword.getPassword(), user.getPassword())){
            if(user.getRole().equals("Admin")){
//            System.out.println("Admin");
                return Roles.ADMIN;
            }
            else if(user.getRole().equals("user")){
                String path=ctx.path();
                if(path.indexOf("users")!=-1){
                    int check= (int) path.chars().filter(ch -> ch == '/').count();
                   if(check==2){
                       if(Integer.parseInt(ctx.pathParam("id"))==user.getId()){
                           return Roles.USER;
                       }
                       else {
                           return null;
                       }
                   }
                   else {
                       return Roles.USER;
                   }
                }
                else if(path.indexOf("Library")!=-1){
                    int checkLibrary=(int) path.chars().filter(ch -> ch == '/').count();
                    System.out.println("work");
                    if(checkLibrary==3){
                        if(Integer.parseInt(ctx.pathParam("user"))==user.getId()){
                            ArrayList<UserGame> library= (ArrayList<UserGame>) libraryDao.queryForEq("gameId",Integer.parseInt(ctx.pathParam("game")));
                            int userId=Integer.parseInt(ctx.pathParam("user"));
                            for(int i=0;i<=library.size();i++){
                                if(userId==library.get(i).getUserId().getId()){
                                    return Roles.USER;
                                }
                            }

                        }
                    }
                    else{
                        return Roles.USER;
                    }
                }
            }
            else {
                return null;
            }
        }
        else{
            ctx.status(403);
            return null;
        }
        return null;
    }
}
