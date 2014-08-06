package models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    //一般性的查询
    public ResultSet Query(String statement) {
        try {
            query = conn.prepareStatement(statement);
            return query.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //查询结果仅为一个整数的查询
    public int GetInt(String statement) throws SQLException {
        query = conn.prepareStatement(statement);
        ResultSet ans = query.executeQuery();
        ans.next();
        return ans.getInt(1);
    }
    //重新执行上一次查询
    public ResultSet ReQuery() {
        try {
            return query.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
