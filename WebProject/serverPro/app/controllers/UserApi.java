package controllers;

import models.JudgeLan;
import net.sf.json.JSONObject;
import play.mvc.Controller;

import static models.Shopping.addShopingcart;
import static models.Shopping.getShopping;

/**
 * Created by Administrator on 2014/8/17.
 */
//获取购物车信息
public class UserApi extends Controller {

    public static void GetShoppingCartInfo(){
       String loginUser= session.get("userId");
        int loginUserId= Integer.parseInt(loginUser);
        String localDBHost=request.host;
        System.out.print("\n"+"localDBHost:  "+localDBHost);
        JSONObject dateJson=new JSONObject();
        JSONObject returnJson=new JSONObject();
        dateJson=getShopping(loginUserId,localDBHost);
        returnJson.put("code","0");
        returnJson.put("msg","obtain shopcart msg");
        returnJson.put("data",dateJson);
        renderJSON(returnJson);

    }
//添加到购物车
    public  static  void  GetAddCartResult(){
        int resourceId=params.get("book_id",int.class);
        int resourceAccount =params.get("book_account",int.class);
        String loginUser=session.get("userId");
        int loginUserId= Integer.parseInt(loginUser);
        int  shopCartId=addShopingcart(loginUserId,resourceId,resourceAccount);
        System.out.print("\n"+"shopcartId:"+shopCartId+"\n");
        JSONObject obj= new JSONObject();
        JSONObject obj2=new JSONObject();
        if(shopCartId!=-1){
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            obj.put("access_method", IS_LAN ? "local" : "remote");
            obj.put("code",0);
            obj.put("msg","添加商品成功");
            obj2.put("addcartresult","success");
            obj.put("data",obj2);
            renderJSON(obj.toString());
        }else{

            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            obj.put("access_method", IS_LAN ? "local":"remote");
            obj.put("code",1);
            obj.put("msg","添加商品失败");
//            obj.put("addcartresult","fail");
            obj2.put("addcartresult","fail");
            obj.put("data",obj2);
            renderJSON(obj.toString());

        }
    }
}
