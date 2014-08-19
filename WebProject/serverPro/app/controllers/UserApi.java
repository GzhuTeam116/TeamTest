package controllers;

import models.JudgeLan;
import models.SqlConnect;
import net.sf.json.JSONObject;
import play.db.DB;
import play.mvc.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Login.RegisterResultApi;
import static models.Shopping.addShopingcart;
import static models.Shopping.deleteShopCart;
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
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        returnJson.put("access_method", IS_LAN ? "local":"remote");
        renderJSON(returnJson);

    }
//添加到购物车
    public  static  void  GetAddCartResult(){
        int resourceId=params.get("book_id",int.class);
        int resourceAccount =params.get("book_account",int.class);
        JSONObject obj= new JSONObject();
        JSONObject obj2=new JSONObject();
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        obj.put("access_method", IS_LAN ? "local" : "remote");
        if(resourceId==0 ||resourceAccount==0){
            obj.put("code",1);
            obj.put("msg","参数有误，添加失败");
            obj2.put("addcartresult","faile");
            obj.put("data",obj2);
            System.out.print("\n" + "GetAddCartResult: " + "资源id，资源数量为空" + "\n");
            renderJSON(obj.toString());
        }
        String loginUser=session.get("userId");
        int loginUserId= Integer.parseInt(loginUser);
        int  shopCartId=addShopingcart(loginUserId,resourceId,resourceAccount);
        System.out.print("\n"+"shopcartId:"+shopCartId+"\n");

        if(shopCartId!=-1){

            obj.put("code",0);
            obj.put("msg","添加商品成功");
            obj2.put("addcartresult","success");
            obj.put("data",obj2);
            renderJSON(obj.toString());
        }else{

            obj.put("code",1);
            obj.put("msg","添加商品失败");
            obj2.put("addcartresult","faile");
            obj.put("data",obj2);
            renderJSON(obj.toString());

        }
    }
 //用户登录
    public  static  void VerifyLogin() throws SQLException {
        String userName=params.get("userName");
        String  password =params.get("password");
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        JSONObject obj=new JSONObject();
        obj.put("access_method",IS_LAN?"local":"remote");
        if(userName==""||password==""){
            obj.put("code",1);
            obj.put("msg","verify");
            obj.put("data","faile");
            System.out.print("\n" + "VerifyLogin: " + " userName or password is null" + "\n");
            renderJSON(obj);
        }
        SqlConnect sqlconn=new SqlConnect(DB.getConnection());
        String sql="select tid from t_user where account='"+userName+"' and password='"+password+"'";
        ResultSet res=sqlconn.Query(sql);
        String loginUser=session.get("userId");
        int loginUserId= Integer.parseInt(loginUser);
        while( res.next()){
           int uerId=res.getInt("tid");
            if(uerId==loginUserId){
                obj.put("code",0);
                obj.put("msg","verify");
                obj.put("data","success");
                renderJSON(obj);
            }else{
                obj.put("code",1);
                obj.put("msg","verify");
                obj.put("data","faile");
                renderJSON(obj);
            }
        }
    }
//用户注册
    public  static  void  RegisterResult() throws SQLException {
        String userAccount=params.get("account");
        String password=params.get("password");
        String phone=params.get("phone");
        String card_num=params.get("card_num");
        String weibo_account=params.get("weibo_account");
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        JSONObject obj=new JSONObject();
        obj.put("access_method",IS_LAN?"local":"remote");
        if(userAccount==""||password==""||phone==""){
            obj.put("code","1");
            obj.put("msg","register");
            obj.put("data","faile");
            System.out.print("\n"+"RegisterResult :"+"用户信息有误"+"\n");
            renderJSON(obj);
        }
        int registerUserId=RegisterResultApi(userAccount,password, phone, card_num, weibo_account);
        if(registerUserId==0){
            obj.put("code","1");
            obj.put("msg","register");
            obj.put("data","faile");
            System.out.print("\n"+"RegisterResult :"+"用户注册失败"+"\n");
            renderJSON(obj);

        }else {
            obj.put("code","0");
            obj.put("msg","register");
            obj.put("data","success");
            System.out.print("\n"+"RegisterResult :"+"用户注册成功"+"\n");
            renderJSON(obj);
        }
    }
 //请求删除购物车条目
    public  static  void  GetDelCartItem()throws SQLException{
      int shopCartId=params.get("shopcartid",int.class);
       JSONObject obj=new JSONObject();
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        obj.put("access_method",IS_LAN?"local":"remote");
        if(shopCartId==0){
            obj.put("code","1");
            obj.put("msg","del_cartitem");
            obj.put("data","faile");
            System.out.print("\n"+"GetDelCartItem: shopCartId is null"+"\n");
            renderJSON(obj);
        }else {
           int result= deleteShopCart(shopCartId);
            if(result==0){
                obj.put("code","1");
                obj.put("msg","del_cartitem");
                obj.put("data","faile");
                System.out.print("\n"+"GetDelCartItem: delete fail"+"\n");
                renderJSON(obj);
            }else {
                obj.put("code","0");
                obj.put("msg","del_cartitem");
                obj.put("data","success");
                System.out.print("\n"+"GetDelCartItem: delete success"+"\n");
                renderJSON(obj);
            }
        }
    }
  //支付
  public  static  void   GetPurchaseResult()throws  SQLException{

  }

}
