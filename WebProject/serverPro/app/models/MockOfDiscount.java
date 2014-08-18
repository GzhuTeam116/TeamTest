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
public class MockOfDiscount {
    public static JSONArray TopTen() {
        JSONArray ret = new JSONArray();
        try {
            SqlConnect sql = new SqlConnect(DB.getConnection());
            String sqlStr = "Select tid, url, name From t_resource Limit 10";
            ResultSet ans = sql.Query(sqlStr);
            for (int i = 0; ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("promotions_id", ans.getInt("tid"));
                node.put("promotions_img1", ans.getString("url"));
                node.put("promotions_tag", ans.getString("name"));
                ret.add(i, node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MockOfDiscount.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public static JSONArray GetList() {
        JSONArray ret = new JSONArray();
        try {
            SqlConnect sql = new SqlConnect(DB.getConnection());
            String sqlStr = "Select tid, url, name, price From t_resource";
            ResultSet ans = sql.Query(sqlStr);
            for (int i = 0; ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("book_id", ans.getInt("tid"));
                node.put("book_img", ans.getString("url"));
                node.put("book_name", ans.getString("name"));
                node.put("book_promotions_price", ans.getDouble("price")*0.8);
                ret.add(i, node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MockOfDiscount.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public static JSONObject LocDiscount(int rid) throws SQLException {
        JSONObject ret = new JSONObject();
        if ((rid&1)==1) throw new SQLException();
        ret.put("discount_img_url", "/public/images/discount.jpg");
        ret.put("discount_id", rid);
        ret.put("discount_promotion", "开业促销，该区域所有图书八折！");
        return ret;
    }
}
