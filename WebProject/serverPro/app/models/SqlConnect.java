package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if (ans != null && ans.next()) return ans.getInt(1);
        else return 0;
    }
    //查询结果仅为一个字符串的查询语句
    public String GetString(String statement) throws SQLException {
        query = conn.prepareStatement(statement);
        ResultSet ans = query.executeQuery();
        if (ans != null && ans.next()) return ans.getString(1);
        else return null;
    }
    //查询结果仅为一个二进制串的查询语句
    public byte[] GetBytes(String statement) throws SQLException {
        query = conn.prepareStatement(statement);
        ResultSet ans = query.executeQuery();
        if (ans != null && ans.next()) return ans.getBytes(1);
        else return null;
    }
    //处理数据修改语句
    public int Update(String statement) {
        try {
            query = conn.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
            query.executeUpdate(); ResultSet rid = query.getGeneratedKeys();
            if (rid!=null &&rid.next()) return rid.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    //设置查询语句，但暂时不执行
    public SqlConnect SetStatement(String statement) throws SQLException {
        query = conn.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
        return this;
    }
    //获取当前查询语句对象
    public PreparedStatement Statement() {
        return query;
    }
    //执行当前数据修改语句
    public int Update() {
        try {
            query.execute(); ResultSet rid = query.getGeneratedKeys();
            if (rid!=null &&rid.next()) return rid.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    //执行当前查询语句
    public ResultSet Query() {
        try {
            return query.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
