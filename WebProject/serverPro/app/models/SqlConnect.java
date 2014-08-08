package models;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class SqlConnect {
    private final Connection conn;
    private PreparedStatement query;
    public SqlConnect(Connection connection) {
        conn = connection;
    }
    //处理一般性的查询语句
    public ResultSet Query(String statement) {
        try {
            query = conn.prepareStatement(statement);
            return query.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //查询结果仅为一个整数的查询语句
    public int GetInt(String statement) throws SQLException {
        query = conn.prepareStatement(statement);
        ResultSet ans = query.executeQuery();
        ans.next();
        return ans.getInt(1);
    }
    //查询结果仅为一个字符串的查询语句
    public String GetString(String statement) throws SQLException {
        query = conn.prepareStatement(statement);
        ResultSet ans = query.executeQuery();
        ans.next();
        return ans.getString(1);
    }
    //处理数据修改语句
    public void Update(String statement) {
        try {
            conn.prepareStatement(statement).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //重新执行上一次执行的语句
    public ResultSet ReExecute() {
        try {
            return query.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
