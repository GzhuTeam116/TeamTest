package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.db.DB;
import play.mvc.*;
import play.Play;
import play.libs.Files;
import play.mvc.*;
import models.JudgeLan;
import models.Login;
import models.SqlConnect;

public class Application extends Controller  {
    public static String USER_IP;//用户访问的ip
    public  static  Boolean IS_LAN;//是否局域网
    public static void index() {
        redirect("../../public/index.html");
    }
    public static void login() {
        String userName = params.get("userName");
        String password=params.get("password");
        Login  userLogin =new Login();
        Integer u_id;
        u_id= userLogin.UserLogin(userName, password);
        if (u_id==0){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "1");
            jsonObject.put("msg", "登录失败，账号密码不存在");
            jsonObject.put("data","fail");
            String res = jsonObject.toString();
            renderJSON(res);
        }else{
            USER_IP= request.remoteAddress;
            System.out.print("\n"+"user ip: "+USER_IP+"\n");
            session.put("userId",u_id);
            JudgeLan judgeLan= new JudgeLan();
            IS_LAN=judgeLan.JudgeLan(USER_IP);
            System.out.print("\n"+"IS_LAN: "+IS_LAN+"\n");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "登录成功");
            jsonObject.put("data", "success");
            if(IS_LAN){
                jsonObject.put("access_method", "local");
            }else {
                jsonObject.put("access_method", "remote");
            }
            String res = jsonObject.toString();
            renderJSON(res);

        }




    }
    public  static  void  userLoginOut(){
        System.out.print("loginOut");
        session.clear();
        renderText("logOut  success");
    }
    
    public static void GetNavigationInfo() {
        try {
            int area_id = params.get("area_id", int.class);
            int book_id = params.get("book_id", int.class);
            String sqlStatement = "Select t_shelf.regional_id\n";
            sqlStatement += "From t_resource RES, t_shelf\n";
            sqlStatement += "Where RES.tid = "+book_id+" and RES.localtion = t_shelf.tid";
            SqlConnect sql = new SqlConnect(DB.getConnection());
            int aim_Id = sql.GetInt(sqlStatement);
            
            JSONObject ans = new JSONObject();
            ans.put("code", "0");
            ans.put("msg", "寻找到的路径如下：");
            ans.put("access_method", IS_LAN ? "local":"remote");
            JSONArray path = new JSONArray();
            for (int i = 0; area_id != aim_Id; ++i) {
                sqlStatement = "Select nextId, direction\n";
                sqlStatement += "From t_adjacency\n";
                sqlStatement += "Where startId = "+area_id+" and endId = "+aim_Id;
                ResultSet next = sql.Query(sqlStatement);
                JSONObject pathNode = new JSONObject();
                sqlStatement = "Select regionalName From t_regional Where tid = "+next.getInt("nextId");
                String areaName = sql.GetString(sqlStatement);
                pathNode.put("area_id", next.getInt("nextId"));
                pathNode.put("area_name", areaName);
                pathNode.put("direction", next.getInt("direction"));
                path.add(i, pathNode);
            }
            ans.put("Navigation_Info", path);
            renderJSON(ans.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public static  void  upLoad(File f)  {
       System.out.print("f:" +f+"\n");
       String path=f.toString();
       String  a="\\";
       System.out.print("\n"+path.substring(path.lastIndexOf(a)+1)+"\n");
       String filename=path.substring(path.lastIndexOf(a)+1);
       Files.copy(f, Play.getFile("public/images/" + filename));
       renderText(Play.getFile("public/images/" + filename));
   }
}