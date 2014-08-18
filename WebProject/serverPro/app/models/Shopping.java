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

    public static List getShopping(int arg_useId) {
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






}
