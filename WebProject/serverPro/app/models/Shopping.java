package models;

import play.db.DB;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

       try{
           Connection conn=DB.getConnection();
           PreparedStatement stams=null;
           java.sql.Statement stmt=null;
           String sql="select b.order_id from t_shopcart a, t_order b where a.orderid=b.order_id and b.order_status='0' and a.u_id='"+arg_userId+"'";
           System.out.print("\n"+"orderSql:  "+sql+"\n");
           stams=conn.prepareStatement(sql);
           stmt=conn.createStatement();
           ResultSet reSet=stams.executeQuery(sql);
           System.out.print("\n"+"reSet.next:"+reSet.next()+"\n");
           //unPay order unExit
           if(reSet.next()==false){
               Date date=new Date();
               SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
               String orderNum=sdf.format(date)+arg_userId;
               System.out.print("\n"+"orderNum:  "+orderNum+"\n");
               String insertOrdersql="insert into t_order (order_num,order_status)values(?,?)";
               PreparedStatement ps=conn.prepareStatement(insertOrdersql,stmt.RETURN_GENERATED_KEYS);
               ps.setString(1,orderNum);
               ps.setString(2,"0");
               ps.execute();
               ResultSet rs = ps.getGeneratedKeys();
               int orderId=0;//保存生成的ID
               if (rs != null&&rs.next()) {
                   orderId=rs.getInt(1);
              String insertShopcartsql="";

               }
               System.out.print("\n"+"orderId:  "+orderId+"\n");
           }
           //unPay order exit
//           while (reSet.next()){
//
//           }

       }catch (Exception e){
           e.printStackTrace();
       };
       return true;
   }


}
