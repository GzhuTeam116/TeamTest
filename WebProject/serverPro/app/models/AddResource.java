package models;

import controllers.Application;
import java.io.IOException;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jance on 2014/8/8.
 */
public class AddResource {
//    AddResource.AddParams addParams = new AddParams();
//     public  static  Connection conn = DB.getConnection();
    public static List getSpecies() {
        List getSpeciesArr = new ArrayList<ResourceSpecies>();
        try {
            Connection conn = DB.getConnection();
            PreparedStatement pstmts = null;
            String sql = "select * from t_species";
            pstmts = conn.prepareStatement(sql);
            ResultSet selectRes = pstmts.executeQuery(sql);
            selectRes.getRow();
            while (selectRes.next()) {
                ResourceSpecies getSpeciesArrInfo = new ResourceSpecies();
                getSpeciesArrInfo.setId(selectRes.getInt("tid"));
                getSpeciesArrInfo.setName(selectRes.getString("speciesName"));
                getSpeciesArr.add(getSpeciesArrInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getSpeciesArr;
    }

    public static List getShelf() {
        List getShelfArr = new ArrayList<Shelf>();
        try {
            Connection conn = DB.getConnection();
            PreparedStatement pstmts = null;
            String sql = "select * from t_shelf";
            pstmts = conn.prepareStatement(sql);
            ResultSet selectRes = pstmts.executeQuery(sql);
            while (selectRes.next()) {
                Shelf getShelfInfo = new Shelf();
                getShelfInfo.setId(selectRes.getInt("tid"));
                getShelfInfo.setName(selectRes.getString("shelf_name"));
                getShelfArr.add(getShelfInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getShelfArr;
    }

    public  static  int  Add(String bookName,String bookPrice,String bookPublish,String bookNum,String bookISBN,String bookAuthor,String picUrl,String selectedSpecies,String selectedShelf ){
       try{
           SqlConnect sqlcon = new SqlConnect(DB.getConnection());
           Connection conn = DB.getConnection();
           Statement stmt=null;
           stmt=conn.createStatement();
          String url = picUrl.replaceAll("\\\\", "/");
//           Uri url=new Uri(picUrl) ;
//           System.out.print("\n"+url+"\n");
          String picName=url.substring(url.lastIndexOf("/")+1);
          String finalUrl="public/images/"+picName;
          System.out.print("\n"+"finalUrl: "+finalUrl+"\n");
          String sql="insert into t_resource(name,price,press,number,isbn,author,url,species_id,location,is_onsall)values"
                   +"('"+bookName+"','"+bookPrice+"','"+bookPublish+"','"+bookNum+"','"+bookISBN+"','"+bookAuthor+"','"+finalUrl+"','"+selectedSpecies+"','"+selectedShelf+"','1')";
           System.out.print("\n"+"sql"+sql+"\n");
           sqlcon.Update(sql);
  //         stmt.executeUpdate(sql);
           sql = "Select tid From t_resource where name = '"+bookName+"' and isbn = '"+bookISBN+"' and author = '"+bookAuthor+"' and press = '"+bookPublish+'\'';
      /*    ResultSet book_id = stmt.executeQuery(sql);
          book_id.next();*/
          int tid = sqlcon.GetInt(sql);
              FullText.Writer indexer = new FullText.Writer();
              indexer.AddIndex(tid);
              indexer.Close();
          } catch (IOException ex) {
              Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
          } catch (SQLException ex) {
            Logger.getLogger(AddResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
     }


   public  static  List getResourceList(){
      List getResourceArr=new ArrayList<GetResourceList>();
      try{
        Connection conn=DB.getConnection();
        PreparedStatement pstms=null;
        String sql="select a.* ,b.shelf_name from t_resource a,t_shelf b where b.tid=a.location";
        pstms=conn.prepareStatement(sql);
        ResultSet set =pstms.executeQuery(sql);
        while (set.next()){
            GetResourceList getResourceListInfo=new GetResourceList();
//            getSpeciesArrInfo.setId(selectRes.getInt("tid"));
            getResourceListInfo.setResourceId(set.getInt("tid"));
            getResourceListInfo.setBookName(set.getString("name"));
            getResourceListInfo.setBookPrice(set.getDouble("price"));
            getResourceListInfo.setBookPublish(set.getString("press"));
            String url=set.getString("url");
            String finalUrl=url.substring(url.lastIndexOf("/")+1);
            getResourceListInfo.setPicUrl("images/"+finalUrl);
            getResourceListInfo.setShelfName(set.getString("shelf_name"));
            getResourceArr.add(getResourceListInfo);
        }
      }catch (Exception e){e.printStackTrace();}

      return getResourceArr;
  }

}
