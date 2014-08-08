/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//这是一个用于调试（而不是测试）的类
//可以在这里面调用正在开发的model，然后用浏览器访问这个类，从而进行调试
package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import play.mvc.Controller;
import models.Floyd;

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
}
