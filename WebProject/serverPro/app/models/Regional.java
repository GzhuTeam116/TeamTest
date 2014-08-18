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
    SqlConnect sql;
    public Regional() {
        sql = new SqlConnect(DB.getConnection());
    }
    public static byte[] UUid2Bytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
    public ResultSet GetInfoFromUUID(byte[] uuid) throws SQLException {
        String sqlStatement = "Select * From t_regional Where uuid = ?";
        sql.SetStatement(sqlStatement).Statement().setBytes(1, uuid);
        ResultSet ans = sql.Query(); ans.next();
        return ans;
    }
    public int RegionalBookIn(int bookId) throws SQLException {
        String sqlStatement = "Select t_shelf.regional_id\n";
        sqlStatement += "From t_resource RES, t_shelf\n";
        sqlStatement += "Where RES.tid = "+bookId+" and RES.localtion = t_shelf.tid";
        return sql.GetInt(sqlStatement);
    }
    public ResultSet GetNext(int area, int aim) throws SQLException {
        String sqlStatement = "Select nextId, direction\n";
        sqlStatement += "From t_adjacency\n";
        sqlStatement += "Where startId = "+area+" and endId = "+aim;
        ResultSet next = sql.Query(sqlStatement);
        next.next(); return next;
    }
    public String RegionalName(int area) throws SQLException {
        String sqlStatement = "Select regionalName From t_regional Where tid = "+area;
        return sql.GetString(sqlStatement);
    }
    public Boolean RegionalDiscount(int area) {
        //TODO: try to get the discount info in this area
        return (area&1) == 0;
    }
}
