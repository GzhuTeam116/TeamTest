package models;

import net.sf.json.JSONObject;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jance on 2014/8/8.
 */
public class AddResource {



    public  static List getSpecies(){
        List getSpeciesArr=new ArrayList<ResourceSpecies>();
        try{
            Connection conn= DB.getConnection();
            PreparedStatement pstmts = null;
            String sql="select * from t_species";
            pstmts=conn.prepareStatement(sql);
            ResultSet selectRes=pstmts.executeQuery(sql);
            selectRes.getRow();
            while (selectRes.next()){
                ResourceSpecies  getSpeciesArrInfo=new ResourceSpecies();
                getSpeciesArrInfo.setId(selectRes.getInt("tid"));
                getSpeciesArrInfo.setName(selectRes.getString("speciesName"));
                getSpeciesArr.add(getSpeciesArrInfo);
            }
        }catch (Exception e){e.printStackTrace();}
        return getSpeciesArr;
    }
    public  static List getShelf(){
        List getShelfArr=  new ArrayList<Shelf>();
        try{
            Connection conn= DB.getConnection();
            PreparedStatement pstmts = null;
            String sql="select * from t_shelf";
            pstmts=conn.prepareStatement(sql);
            ResultSet selectRes=pstmts.executeQuery(sql);
            while (selectRes.next()){
                Shelf getShelfInfo=new Shelf();
                getShelfInfo.setId(selectRes.getInt("tid"));
                getShelfInfo.setName(selectRes.getString("shelf_name"));
                getShelfArr.add(getShelfInfo);
            }
        }catch (Exception e){e.printStackTrace();}
        return getShelfArr;
    }
}
