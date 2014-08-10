/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//这是一个用于调试（而不是测试）的类
//可以在这里面调用正在开发的model，然后用浏览器访问这个类，从而进行调试
package controllers;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Floyd;
import models.Regional;
import models.SqlConnect;
import play.db.DB;
import play.mvc.Controller;

/**
 *
 * @author User
 */
public class Debug extends Controller {
    public static void Floyd() {
        try {
            Floyd creater = new Floyd();
            creater.CreateAdjace();
        } catch (SQLException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void SetUUID() {
        try {
            byte[] uuid = Regional.UUid2Bytes(UUID.fromString(params.get("uuid")));
            int rtid = params.get("tid", int.class);
            String sqlStatement = "Update t_regional Set uuid = ? Where tid = "+rtid;
            SqlConnect sql = new SqlConnect(DB.getConnection());
            sql.SetStatement(sqlStatement).Statement().setBytes(1, uuid);
            sql.Update();
        } catch (SQLException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
