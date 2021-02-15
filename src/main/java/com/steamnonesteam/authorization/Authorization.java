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
    final String nickname="nickname";
    final String admin="Admin";
    final String userString="user";
    final String users="users";
    final String idString="id";
    final String gameId="gameId";
    final String library="Library";
    final int notHere=-1;
    final int userChecker=2;
    final int libraryChecker=3;
    final int zero=0;
    public Authorization(Dao<User, Integer> dao,Dao<UserGame,Integer> libraryDao)
    {
        this.dao = dao;
        this.libraryDao=libraryDao;
    }
    public Roles getUserRole(Context ctx,User user){
        String path=ctx.path();
        if(path.indexOf(users)!=notHere) {
            int check = (int) path.chars().filter(ch -> ch == '/').count();
            if (check == userChecker) {
                if (Integer.parseInt(ctx.pathParam(idString)) == user.getId()) {
                    return Roles.USER;
                } else {
                    return null;
                }
            } else {
                return Roles.USER;
            }
        }
        return null;
    }
    public Roles libraryChecker(Context ctx,User user) throws SQLException {
        String path=ctx.path();
        int checkLibrary=(int) path.chars().filter(ch -> ch == '/').count();
        if(checkLibrary==libraryChecker){
            if(Integer.parseInt(ctx.pathParam(userString))==user.getId()){
                ArrayList<UserGame> library= (ArrayList<UserGame>) libraryDao.queryForEq(gameId,Integer.parseInt(ctx.pathParam("game")));
                int userId=Integer.parseInt(ctx.pathParam(userString));
                for(int i=zero;i<=library.size();i++){
                    if(userId==library.get(i).getUserId().getId()){
                        return Roles.USER;
                    }
                }
            }
        }
        else{
            return Roles.USER;
        }
        return  null;
    }
    public Roles getUserRole(Context ctx) throws SQLException {
        if(ctx.basicAuthCredentials().getUsername().equals("")){
            return Roles.USER;
        }
        BasicAuthCredentials loginPassword=ctx.basicAuthCredentials();
        User user=dao.queryForEq(nickname,loginPassword.getUsername()).get(0);
        if(BCrypt.checkpw(loginPassword.getPassword(), user.getPassword())){
            if(user.getRole().equals(admin)){
                return Roles.ADMIN;
            }
            else if(user.getRole().equals(userString)){
                String path=ctx.path();
                if(path.indexOf(users)!=notHere){
                    return getUserRole(ctx,user);
                }
                else if(path.indexOf(library)!=notHere){
                    return libraryChecker(ctx,user);
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
