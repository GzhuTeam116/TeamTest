package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jance on 2014/8/1.
 */
public class Login {

    public Integer UserLogin(String arg_account, String arg_password)  {
        try {
            Connection con=null;
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "123"); //链接本地MYSQL
            Statement stmt; //创建声明
           stmt = con.createStatement();
         String selectSql = "SELECT * FROM t_user where account="+arg_account+" and password="+arg_password;
         ResultSet selectRes = stmt.executeQuery(selectSql);
         while (selectRes.next()) {
            String account = selectRes.getString("account");
            String password = selectRes.getString("password");
            Integer u_id=selectRes.getInt("tid");
//            System.out.print("\r\n\r\n");
            System.out.print("loginClass% "+"account:" + password + "password:" + password+"tid:"+u_id);
            return u_id;
        }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return 0;
    }
 }