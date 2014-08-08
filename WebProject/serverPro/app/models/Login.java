package models;

import play.db.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jance on 2014/8/1.
 */
public class Login {

    public static Integer UserLogin(String arg_account, String arg_password)  {
        try {
            SqlConnect sql = new SqlConnect(DB.getConnection());
            String selectSql = "SELECT * FROM t_user where account='"+arg_account+"' and password= '"+arg_password+"' and is_admin=1";
            ResultSet selectRes = sql.Query(selectSql);
            while (selectRes.next()) {
                String account = selectRes.getString("account");
                String password = selectRes.getString("password");
                Integer u_id=selectRes.getInt("tid");
                System.out.print("\r\n\r\n");
                System.out.print("loginClass% "+"account:" + password + "password:" + password+"tid:"+u_id);
                return u_id;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
 }