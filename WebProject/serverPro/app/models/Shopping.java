package models;

import net.sf.json.JSONObject;
import play.db.DB;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/8/17.
 */
public class Shopping {

    public static JSONObject getShopping(int arg_useId,String arg_localDBHost) {
//       JSONObject
//        List shopcartArr = new ArrayList<GetShopArr>();
        List shopCartInfoArr = new ArrayList<GetShopInfo>();
        JSONObject orderJson =new JSONObject();
        try {
            Connection conn=DB.getConnection();
//        String sql="select a.* ,b.*from t_order a, t_shopcart b where a.order_id=b.orderid and a.order_status ='0'and b.u_id='"+arg_useId+"'";
            String getOrderSql="SELECT order_id, order_num  FROM t_order   where order_status='0' and order_id in (SELECT  distinct orderid from t_shopcart  where u_id='"+arg_useId+"')";
            PreparedStatement pstms= conn.prepareStatement(getOrderSql);
            ResultSet set=pstms.executeQuery(getOrderSql);
            while (set.next()){
                orderJson.put("orderform_num",set.getString("order_num"));
                int orderId=set.getInt("order_id");
                String getShopCartSql="select a.tid,a.number, b.tid,b.url, b.name,b.price from t_shopcart a , t_resource b where a.resource_id=b.tid and a.orderid='"+ orderId+"' and u_id='"+arg_useId+"'";
                pstms= conn.prepareStatement(getShopCartSql);
                set=pstms.executeQuery(getShopCartSql);
                double orderTotalPrice=0;
                String url="";
                while (set.next()){
                    GetShopInfo getShopInfo =new GetShopInfo();
                    getShopInfo.setShopCartId(set.getInt("a.tid"));
                    getShopInfo.setBookId(set.getInt("b.tid"));
                    getShopInfo.setBookImg("http://"+arg_localDBHost+"/"+set.getString("b.url"));
                    getShopInfo.setBookName(set.getString("b.name"));
                    getShopInfo.setBoookPrice(set.getDouble("b.price"));
                    getShopInfo.setBookCount(set.getInt("a.number"));
                    double perPrice=set.getDouble("b.price");
                    int num=set.getInt("a.number");
                    orderTotalPrice+=perPrice*num;
                    url=set.getString("b.url");
                    shopCartInfoArr.add(getShopInfo);
                }

                orderJson.put("orderform_price",orderTotalPrice);
                orderJson.put("shopcart",shopCartInfoArr);
                java.sql.Statement stmt = conn.createStatement();
                String updateOrderSql="update t_order set order_total='"+orderTotalPrice+"' , order_img='"+url+"' where order_id='"+orderId+"'";
                stmt.execute(updateOrderSql);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderJson;
    }
    public  static  int  addShopingcart(int arg_userId,int arg_resourceId,int arg_resourceAccount){

       try{
           SqlConnect sqlcon = new SqlConnect(DB.getConnection());
           Connection conn=DB.getConnection();
           PreparedStatement stams=null;
           java.sql.Statement stmt=null;
           String sql="select b.order_id from t_shopcart a, t_order b where a.orderid=b.order_id and b.order_status='0' and a.u_id='"+arg_userId+"'";
           System.out.print("\n"+"orderSql:  "+sql+"\n");
           stams=conn.prepareStatement(sql);
           stmt=conn.createStatement();
            Boolean result=stams.executeQuery(sql).next();
           //unPay order unExit
           if(!result){
               Date date=new Date();
               SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
               String orderNum=sdf.format(date);
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
                   if (orderId!=0){
                       java.sql.Statement sts=null;
                       sts = conn.createStatement();
                       String insertShopcartsql="insert into t_shopcart(u_id,resource_id,number,orderid)values(?,?,?,?)";
                       ps=conn.prepareStatement(insertShopcartsql,sts.RETURN_GENERATED_KEYS);
//                       ps.setInt(1,orderId);
                       ps.setInt(1,arg_userId);
                       ps.setInt(2,arg_resourceId);
                       ps.setInt(3,arg_resourceAccount);
                       ps.setInt(4,orderId);
                       ps.execute();
                       int shopCartId=0;
                       rs=ps.getGeneratedKeys();
                       if(rs!=null&&rs.next()){
                           shopCartId=rs.getInt(1);

                           return shopCartId;
                       }

                   }


               }
               System.out.print("\n"+"orderId:  "+orderId+"\n");
           }else {
               int orderId = sqlcon.GetInt(sql);
               String insertShopcartsql="insert into t_shopcart (u_id,resource_id,number,orderid)values(?,?,?,?)";
               PreparedStatement pst=conn.prepareStatement(insertShopcartsql,stmt.RETURN_GENERATED_KEYS);
               pst.setInt(1, arg_userId);
               pst.setInt(2, arg_resourceId);
               pst.setInt(3, arg_resourceAccount);
               pst.setInt(4, orderId);
                 pst.execute();
               int shopCartId=0;
               ResultSet rs=pst.getGeneratedKeys();
               if(rs!=null&&rs.next()){
                   shopCartId=rs.getInt(1);
                   return shopCartId;
               }

           }

       }catch (Exception e){
           e.printStackTrace();
       };
       return -1;
   }
    public  static  int deleteShopCart(int arg_shopCartId) throws SQLException {
        Connection conn=DB.getConnection();
        java.sql.Statement stmt = conn.createStatement();
        String sql="delete from t_shopcart where tid='"+arg_shopCartId+"'";
        int  deleteRes=stmt.executeUpdate(sql);
        return deleteRes;
    }

}
