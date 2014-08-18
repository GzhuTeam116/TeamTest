package controllers;

import play.mvc.Controller;

/**
 * Created by Administrator on 2014/8/17.
 */
public class UserApi extends Controller {

    public static void GetShoppingCartInfo(){
       String loginUser= session.get("userId");


    }

    public  static  void  GetAddCartResult(){
        String resourceId=params.get("book_id");
        String resourceAccount =params.get("book_account");


    }
}
