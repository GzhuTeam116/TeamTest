package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import models.*;


import static models.AddResource.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Play;
import play.db.DB;
import play.libs.Files;
import play.mvc.*;

public class Application extends Controller  {
    public static void index() {
        redirect("../../public/index.html");
    }
    
    public static void login() {
        String userName = params.get("userName");
        String password = params.get("password");
        Integer u_id;
        u_id= Login.UserLogin(userName, password);
        if (u_id == 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "1");
            jsonObject.put("msg", "登录失败，账号密码不存在");
            jsonObject.put("data","fail");
            String res = jsonObject.toString();
            renderJSON(res);
        } else {
            System.out.print("\n"+"user ip: "+request.remoteAddress+"\n");
            session.put("userId",u_id);
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            System.out.print("\n"+"IS_LAN: "+IS_LAN+"\n");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "登录成功");
            jsonObject.put("data", "success");
            if (IS_LAN) {
                jsonObject.put("access_method", "local");
            } else {
                jsonObject.put("access_method", "remote");
            }
            String res = jsonObject.toString();
            renderJSON(res);
        }
    }
    
    public  static  void  userLoginOut(){
        System.out.print("loginOut");
        session.clear();
        renderText("logOut  success");
    }
    
    public static void GetNavigationInfo() {
        Regional regs = new Regional();
        try {
            int area_id = params.get("area_id", int.class);
            int book_id = params.get("book_id", int.class);
            int aim_id = regs.RegionalBookIn(book_id);
            
            JSONObject ans = new JSONObject();
            ans.put("code", "0");
            ans.put("msg", "寻找到的路径如下：");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ans.put("access_method", IS_LAN ? "local":"remote");
            
            JSONArray path = new JSONArray();
            for (int i = 0; area_id != aim_id; ++i) {
                ResultSet next = regs.GetNext(area_id, aim_id);
                area_id = next.getInt("nextId");
                JSONObject pathNode = new JSONObject();
                String areaName = regs.RegionalName(area_id);
                pathNode.put("area_id", area_id);
                pathNode.put("area_name", areaName);
                pathNode.put("direction", next.getString("direction"));
                path.add(i, pathNode);
            }
            ans.put("Navigation_Info", path);
            renderJSON(ans.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            JSONObject ans = new JSONObject();
            ans.put("code", "1");
            ans.put("msg", "未能寻找到路径！");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ans.put("access_method", IS_LAN ? "local":"remote");
            renderJSON(ans.toString());
        }
    }
//上传 图片
    public static  void  upLoad(File f)  {
        JSONObject jsonObject = new JSONObject();
        if(f==null){
            jsonObject.put("code","1");
            jsonObject.put("msg","请选择图片");
            String res = jsonObject.toString();
            renderJSON(res);
        }
        String path=f.toString();
        String  a="\\";
        System.out.print("\n"+path.substring(path.lastIndexOf(a)+1)+"\n");
        String filename=path.substring(path.lastIndexOf(a)+1);
        Files.copy(f, Play.getFile("public/images/" + filename));
        System.out.print(Play.getFile("public/images/" + filename));
        String url=Play.getFile("public/images/" + filename).toString();
        jsonObject.put("code","0");
        jsonObject.put("msg","上传成功");
        jsonObject.put("url",url);
        String res = jsonObject.toString();
        renderJSON(res);
    }
//获得添加商品的select信息
    public  static  void  addResourceInfo(){
        List speciesNames=getSpecies();
        List shelfNames=getShelf();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("speciesNames",speciesNames );
        jsonObject.put("shelfNames",shelfNames);
        String res = jsonObject.toString();
        renderJSON(res);
    }
    
    public static void GetLocation() {
        Regional regs = new Regional();
        try {
            byte[] uuid = Regional.UUid2Bytes(UUID.fromString(params.get("uuid")));
            ResultSet ans = regs.GetInfoFromUUID(uuid);
            
            JSONObject jsonRet = new JSONObject();
            jsonRet.put("code", 0);
            jsonRet.put("msg", "当前区域信息为：");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            jsonRet.put("access_method", IS_LAN ? "local":"remote");
            
            JSONObject info = new JSONObject();
            info.put("area_id", ans.getInt("tid"));
            info.put("area_name", ans.getString("regionalName"));
            info.put("area_floor", ans.getInt("stairs"));
            info.put("area_east", ans.getInt("east"));
            info.put("area_west", ans.getInt("west"));
            info.put("area_south", ans.getInt("south"));
            info.put("area_north", ans.getInt("north"));
            info.put("area_upstairs", ans.getInt("upstairs"));
            info.put("area_downstairs", ans.getInt("downstairs"));
            info.put("area_discount", regs.RegionalDiscount(ans.getInt("tid"))?1:0);
            
            jsonRet.put("area_info", info);
            renderJSON(jsonRet.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            JSONObject jsonRet = new JSONObject();
            jsonRet.put("code", 1);
            jsonRet.put("msg", "未能查到该区域！");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            jsonRet.put("access_method", IS_LAN ? "local":"remote");
            renderJSON(jsonRet.toString());
        }
    }

  //添加商品
   public  static  void  adminAddShop(){
      String bookName=params.get("bookName");
      String bookPrice=params.get("bookPrice");
      String bookPublish=params.get("bookPublish");
      String bookNum=params.get("bookNum");
      String bookISBN=params.get("bookISBN");
      String bookAuthor=params.get("bookAuthor");
      String  picUrl=params.get("picUrl");
      String selectedSpecies=params.get("selectedSpecies");
      String selectedShelf=params.get("selectedShelf");
      int result =Add(bookName, bookPrice, bookPublish, bookNum, bookISBN, bookAuthor, picUrl, selectedSpecies, selectedShelf);
      if (result==0){
          renderText("success");
      }
      System.out.print(params);
      renderText("fail");

   }

    //获取商品列表
    public  static  void  adminGetList(){
        List resourceList=getResourceList();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("resourceList",resourceList);
        String res=jsonObject.toString();
        renderJSON(res);
   
   
    }

    public static void GetHomePageContent() {
        JSONObject ret = new JSONObject();
        GetTopTen homepage = new GetTopTen();
        ret.put("code", 0);
        ret.put("msg", "获取主页内容");
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        ret.put("access_method", IS_LAN ? "local":"remote");
        ret.put("HotSaleBook", homepage.HotSales());
        ret.put("HobbyBook", homepage.Hobby());
        ret.put("商品分类列表", homepage.GetSpecies());
        ret.put("PromotionsImg", homepage.GetDiscount());
        renderJSON(ret.toString());
    }
    
    public static void GetPromotionsItem() {
        JSONObject ret = new JSONObject();
        ret.put("code", 1);
        ret.put("msg", "促销功能开发中！");
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        ret.put("access_method", IS_LAN ? "local":"remote");
        //TODO: 获取促销信息列表
        ret.put("promotions_item_result", new JSONArray());
        renderJSON(ret.toString());
    }
    
    private static final int PAGE = 20;
    public static void GetSecCategoryItem() {
        JSONObject ret = new JSONObject();
        JSONArray cat = new JSONArray();
        int specie_id = params.get("id", int.class);
        int pn = params.get("page_num", int.class);
        try {
            ret.put("code", 0);
            ret.put("msg", "获取分类商品的列表信息");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ret.put("access_method", IS_LAN ? "local":"remote");
            
            SqlConnect sql = new SqlConnect(DB.getConnection());
            String sqlStatement = "Select tid, url, name, price From t_resource\n";
            sqlStatement += "Where species_id = "+specie_id;
            sqlStatement += " Limit "+(pn-1)*PAGE+", "+PAGE;
            ResultSet ans = sql.Query(sqlStatement);
            
            for (int i = 0; ans.next(); ++i) {
                JSONObject node = new JSONObject();
                node.put("book_id", ans.getInt("tid"));
                node.put("book_img", ans.getString("url"));
                node.put("book_name", ans.getString("name"));
                node.put("book_price", ans.getDouble("price"));
                cat.add(i, node);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            ret.put("code", 1);
            ret.put("msg", "未完全获取该页商品信息");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ret.put("access_method", IS_LAN ? "local":"remote");
        }
        ret.put("SecCategoryIitem", cat);
        renderJSON(ret.toString());
    }
    public static void GetHotSrchBook() {
        JSONObject ret = new JSONObject();
        GetTopTen homepage = new GetTopTen();
        ret.put("code", 0);
        ret.put("msg", "获取热搜推荐信息");
        Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
        ret.put("access_method", IS_LAN ? "local":"remote");
        ret.put("HotSrchBook", homepage.HotSearch());
        renderJSON(ret.toString());
    }
    
    public static void GetDetailBook() {
        int tid = params.get("resourceId", int.class);
        SqlConnect sql = new SqlConnect(DB.getConnection());
        JSONObject ret = new JSONObject();
        try {
            ret.put("code", 0);
            ret.put("msg", "获取商品详情成功");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ret.put("access_method", IS_LAN ? "local":"remote");
            String sqlStr = "Select * From t_resource Where tid = "+tid;
            ResultSet ans = sql.Query(sqlStr); ans.next();
            ret.put("book_id", ans.getInt("tid"));
            ret.put("book_img", ans.getString("url"));
            ret.put("book_price", ans.getDouble("price"));
            ret.put("book_name", ans.getString("name"));
            ret.put("book_author", ans.getString("author"));
            ret.put("book_press", ans.getString("press"));
            ret.put("introduction", ans.getString("introduction"));
            sqlStr = "Select * From t_shelf Where tid = "+ans.getInt("location");
            ans = sql.Query(sqlStr); ans.next();
            JSONObject location = new JSONObject();
            location.put("area_num", ans.getInt("regional_id"));
            location.put("bookshelf_num", ans.getInt("tid"));
            location.put("shelf_name", ans.getString("shelf_name"));
            location.put("shelf_descripe", ans.getString("shelf_descripe"));
            ret.put("location", location);
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            ret.put("code", 1);
            ret.put("msg", "获取商品详情失败");
            Boolean IS_LAN = JudgeLan.JudgeLan(request.remoteAddress);
            ret.put("access_method", IS_LAN ? "local":"remote");
        }
        renderJSON(ret.toString());
    }
}