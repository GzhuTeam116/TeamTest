package models;

import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/8/17.
 */
public class Shopping {

    public static List getShopping(String arg_useId) {
//       JSONObject
        List shopcartArr = new ArrayList<GetShopArr>();
        List shopCartInfoArr = new ArrayList<GetShopInfo>();

        try {
            Connection conn= DB.getConnection();
            PreparedStatement pstmts=null;
            String sql="";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return shopcartArr;
    }



   public  static  boolean  addShopingcart(String arg_userId,String arg_resourceId,String arg_resourceAccount){


       return true;
   }


}
