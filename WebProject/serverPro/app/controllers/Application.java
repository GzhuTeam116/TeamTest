package controllers;
import models.JudgeLan;
import models.Login;
import net.sf.json.JSONObject;
import play.Play;
import play.libs.Files;
import play.mvc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


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
            Boolean IS_LAN=judgeLan.JudgeLan(USER_IP);
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