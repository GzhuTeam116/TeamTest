/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.SQLException;
import java.sql.ResultSet;
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
        int maxtid = sql.GetInt("Select max(tid) From t_regional");
        tid2gid = new int[maxtid+1];
        
        String sqlStatement = "Select tid, east, south, west, north,";
        sqlStatement += "upstairs, downstairs From t_regional";
        ResultSet sqlResult = sql.Query(sqlStatement);
        for (int i = 1; sqlResult.next(); ++i) {
            gid2tid[i] = sqlResult.getInt(1);
            tid2gid[sqlResult.getInt(1)] = i;
        }
        sqlResult = sql.Query();
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
    
    public void CreateAdjace() {
        //Floyd 核心算法
        for (int k = 1; k < dist.length; ++k)
        for (int i = 1; i < dist.length; ++i)
        for (int j = 1; j < dist.length; ++j)
            if (dist[i][k] != 0 && dist[k][j] != 0)
                if (dist[i][j] == 0 || dist[i][j] > dist[i][k]+dist[k][j]) {
                    dist[i][j] = dist[i][k]+dist[k][j];
                    path[i][j] = k;
                }
        //计算每个起点到每个终点的next并写入数据库
        for (int i = 1; i < dist.length; ++i)
            for (int j = 1; j < dist.length; ++j)
                WriteNext(i, j);
    }
    private void WriteNext(int gidfrom, int gidto) {
        if (next[gidfrom][gidto] != 0) return;
        char direct[] = {'\0','E','S','W','N','U','D'};
        if (drct[gidfrom][gidto] == 0) {
            WriteNext(gidfrom,path[gidfrom][gidto]);
            next[gidfrom][gidto] = next[gidfrom][path[gidfrom][gidto]];
        } else next[gidfrom][gidto] = gidto;
        
        int gnext = next[gidfrom][gidto];
        String sqlState = "Insert into t_adjacency(startId, endId, nextId, direction)\n";
        sqlState += "Values("+gid2tid[gidfrom]+','+gid2tid[gidto]+',';
        sqlState += gid2tid[gnext]+",'"+direct[drct[gidfrom][gnext]]+"')\n";
        sqlState += "On duplicate key Update nextId = "+gid2tid[gnext]+',';
        sqlState += "direction = '"+direct[drct[gidfrom][gnext]]+"'";
        sql.Update(sqlState);
    }
}
