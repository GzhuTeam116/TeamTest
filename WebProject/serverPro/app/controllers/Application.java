package controllers;
import models.Login;
import net.sf.json.JSONObject;
import play.Logger;
import play.cache.Cache;
import play.mvc.*;

import java.util.HashMap;

public class Application extends Controller {

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
            session.put("userId",u_id);
//            session.wait();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "登录成功");
            jsonObject.put("data", "success");
            String res = jsonObject.toString();
            renderJSON(res);

        }




    }
    public  static  void  addShopInfo(){
        String u_id;
        u_id=session.get("userId");
        renderText("u_id:"+u_id);

    }

    public  static  void  userLoginOut(){
        System.out.print("loginOut");
        session.clear();
        renderText("logOut  success");
    }
}