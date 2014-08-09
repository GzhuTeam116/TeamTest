/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import play.db.DB;

/**
 *
 * @author User
 */
public class Regional {
    public static byte[] UUid2Bytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
    public static ResultSet GetInfoFromUUID(byte[] uuid) throws SQLException {
        String sqlStatement = "Select * From t_regional Where uuid = ?";
        SqlConnect sql = new SqlConnect(DB.getConnection());
        sql.SetStatement(sqlStatement).Statement().setBytes(1, uuid);
        ResultSet ans = sql.Query(); ans.next();
        return ans;
    }
    public static int RegionalBookIn(int bookId) throws SQLException {
        String sqlStatement = "Select t_shelf.regional_id\n";
        sqlStatement += "From t_resource RES, t_shelf\n";
        sqlStatement += "Where RES.tid = "+bookId+" and RES.localtion = t_shelf.tid";
        return (new SqlConnect(DB.getConnection())).GetInt(sqlStatement);
    }
    public static ResultSet GetNext(int area, int aim) throws SQLException {
        String sqlStatement = "Select nextId, direction\n";
        sqlStatement += "From t_adjacency\n";
        sqlStatement += "Where startId = "+area+" and endId = "+aim;
        SqlConnect sql = new SqlConnect(DB.getConnection());
        ResultSet next = sql.Query(sqlStatement);
        next.next(); return next;
    }
    public static String RegionalName(int area) throws SQLException {
        String sqlStatement = "Select regionalName From t_regional Where tid = "+area;
        return (new SqlConnect(DB.getConnection())).GetString(sqlStatement);
    }
    public static Boolean RegionalDiscount(int area) {
        //TODO: try to get the discount info in this area
        return false;
    }
}
