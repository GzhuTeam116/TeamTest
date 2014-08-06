/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.sql.ResultSet;
import java.sql.SQLException;
import play.db.DB;
/**
 *
 * @author Sine
 */
public class Floyd {
    SqlConnect sql;
    int[] tid2gid, gid2tid;
    int[][] drct, dist, path, next;
    
    public Floyd() throws SQLException {
        sql = new SqlConnect(DB.getConnection());
        
        int regins = sql.GetInt("Select count(*) From t_regional");
        gid2tid = new int[regins+1];
        drct = new int[regins+1][regins+1];
        dist = new int[regins+1][regins+1];
        path = new int[regins+1][regins+1];
        next = new int[regins+1][regins+1];
        int maxtid = sql.GetInt("Select max(*) From t_regional");
        tid2gid = new int[maxtid+1];
        
        String sqlStatement = "Select tid, east, south, west, north,";
        sqlStatement += "upstairs, downstairs From t_regional";
        ResultSet sqlResult = sql.Query(sqlStatement);
        for (int i = 1; sqlResult.next(); ++i) {
            gid2tid[i] = sqlResult.getInt(1);
            tid2gid[sqlResult.getInt(1)] = i;
        }
        sqlResult = sql.ReQuery();
        while (sqlResult.next()) {
            int from = tid2gid[sqlResult.getInt(1)];
            for (int dir = 1; dir <= 6; ++dir) {
                int to = sqlResult.getInt(dir+1);
                if (regins != 0) {
                    to = tid2gid[to];
                    drct[from][to] = dir;
                    dist[from][to] = 1;
                }
            }
        }
    }
}
