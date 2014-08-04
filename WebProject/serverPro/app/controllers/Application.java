package controllers;
import models.Login;
import net.sf.json.JSONObject;
import play.Logger;
import play.cache.Cache;
import play.mvc.*;

import java.util.HashMap;

public class Application extends Controller {

    public static void index() {
//       System.out.print(session.get("userId")+"\n");
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
            jsonObject.put("message", "登录失败，账号密码不存在");
            String res = jsonObject.toString();
            renderJSON(res);
        }else{
//            SetCookie setCookie=new SetCookie();
//            HashMap scache = new HashMap();
//            scache.put("user",u_id);
//            scache.put("sessionID", session.getId());
//            Cache.set("session_"+session.getId(), scache, "30mn");//30 mins session timeout
//            Logger.debug("user " + u_id + " logged in as session " + session.getId());


//            Cache.set(session.getId(), u_id);
            session.put("userId",u_id);
//            String userIdSession=session.getId();
//            System.out.print("\n"+"session"+userIdSession+"\n");
//                    renderText("userName receive: " + userName);
//            renderJSON("{\"code\": " + "0"+ "\"message\":"+"登录成功"+"}");
//            renderJSON("");
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
}