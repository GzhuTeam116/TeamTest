/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.db.DB;

/**
 *
 * @author User
 */
public class GetTopTen {
    SqlConnect sql;
    public GetTopTen() {
        sql = new SqlConnect(DB.getConnection());
    }
    public JSONArray HotSales() {
        JSONArray ret = new JSONArray();
        String sqlStatement = "Select tid, url, salesvolume From t_resource\n";
        sqlStatement += "Order by salesvolume Desc Limit 10\n";
        ResultSet ans = sql.Query(sqlStatement);
        try {
            for (int i = 0; i < 10 && ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("book_id", ans.getInt("tid"));
                node.put("book_img", ans.getString("url"));
                node.put("sales_volume", ans.getInt("salesvolume"));
                ret.add(i, node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetTopTen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public JSONArray HotSearch() {
        JSONArray ret = new JSONArray();
        String sqlStatement = "Select tid, url, salesvolume From t_resource\n";
        sqlStatement += "Order by searchNum Desc Limit 10\n";
        ResultSet ans = sql.Query(sqlStatement);
        try {
            for (int i = 0; i < 10 && ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("book_id", ans.getInt("tid"));
                node.put("book_img", ans.getString("url"));
                node.put("sales_volume", ans.getInt("salesvolume"));
                ret.add(i, node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetTopTen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public JSONArray Hobby() {
        //TODO: 获取用户最喜欢的十本书
        return new JSONArray();
    }
    public JSONArray GetDiscount() {
        //TODO: 获取促销信息
        return new JSONArray();
    }
    public JSONArray GetSpecies() {
        JSONArray ret = new JSONArray();
        String sqlStatement = "Select * From t_species";
        ResultSet ans = sql.Query(sqlStatement);
        try {
            for (int i = 0; ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("sort_id", ans.getInt("tid"));
                node.put("sort_name", ans.getString("speciesName"));
                ret.add(i,node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetTopTen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
