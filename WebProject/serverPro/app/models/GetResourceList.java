package models;

import java.security.PublicKey;

/**
 * Created by jance on 2014/8/10.
 */
public class GetResourceList {
    public  int  resourceId;
    public  String bookName;
    public  double bookPrice;
    public String bookPublish;
    public int  bookNum;
    public String bookISBN;
    public String bookAuthor;
    public  int selectedSpecies;
    public  int   salesvolume;
    public int selectedShelf;
    public int  is_onsall;
    public String picUrl;
    public  String introduction;
    public  String searchNum;
    public  String shelfName;

    public void setResourceId(int arg_resourceId){
        this.resourceId=arg_resourceId;
    }
    public void setBookName(String arg_bookName){
        this.bookName=arg_bookName;
    }
    public void setBookPrice(double arg_bookPrice){
        this.bookPrice=arg_bookPrice;
    }
    public void setBookPublish(String arg_bookPublish){
        this.bookPublish=arg_bookPublish;
    }
    public void setBookNum(int arg_bookNum){
        this.bookNum=arg_bookNum;
    }
    public void setBookISBN(String arg_bookISBN){
        this.bookISBN=arg_bookISBN;
    }
    public void setBookAuthor(String arg_bookAuthor){
        this.bookAuthor=arg_bookAuthor;
    }
    public void setSelectedSpecies(int arg_selectedSpecies){
        this.selectedSpecies=arg_selectedSpecies;
    }
    public void setSalesvolume(int arg_salesvolume){
        this.salesvolume=arg_salesvolume;
    }
    public void setSelectedShelf(int arg_selectedShelf){
        this.selectedShelf=arg_selectedShelf;
    }
    public void setIs_onsall(int arg_is_onsall){
        this.is_onsall=arg_is_onsall;
    }
    public void setPicUrl(String arg_picUrl){
        this.picUrl=arg_picUrl;
    }
    public void setIntroduction(String arg_introduction){
        this.introduction=arg_introduction;
    }
    public void setSearchNum(String arg_searchNum){
        this.searchNum=arg_searchNum;
    }
    public void  setShelfName(String arg_shelfName ){this.shelfName=arg_shelfName;}
}
