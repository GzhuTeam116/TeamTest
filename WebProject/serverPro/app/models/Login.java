package models;

import play.db.DB;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
//            String selectSql = "SELECT * FROM t_user where account='"+arg_account+"' and password= '"+arg_password+"' and is_admin=1";
            String selectSql = "SELECT * FROM t_user where account='"+arg_account+"' and password= '"+arg_password+"'";
            ResultSet selectRes = sql.Query(selectSql);
            if (selectRes != null) while (selectRes.next()) {
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

    public  static  int RegisterResultApi(String arg_account,String arg_password,String arg_phone,String arg_carNum,String arg_weiBoAccount) throws SQLException {
        int registerUserId=0;
        Connection conn=DB.getConnection();
       java.sql.Statement sts=null;
        sts=conn.createStatement();
        String insertSql="insert into t_user  (account,password,is_admin,phone,card_num,weibo_account) values (?,?,?,?,?,?)";
        PreparedStatement pst=conn.prepareStatement(insertSql,sts.RETURN_GENERATED_KEYS);
        pst.setString(1,arg_account);
        pst.setString(2,arg_password);
        pst.setString(3,"0");
        pst.setString(4,arg_phone);
        pst.setString(5,arg_carNum);
        pst.setString(6,arg_weiBoAccount);
        pst.execute();
        ResultSet ret=pst.getGeneratedKeys();
        if(ret!=null && ret.next()){
            registerUserId=ret.getInt(1);
        }
        return registerUserId;
    }
 }