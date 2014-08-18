package models;

/**
 * Created by Administrator on 2014/8/17.
 */
public  class  GetShopInfo {
    int shopcartid;
    int book_id;
    String book_img;
    String book_name;
    double book_price;
    int   book_count;

    public void  setShopCartId(int arg_shopCartId){
        this.shopcartid=arg_shopCartId;
    }
    public void  setBookId(int arg_bookId){
        this.book_id=arg_bookId;
    }
    public  void setBookImg(String arg_bookImg){
        this.book_img=arg_bookImg;
    }
    public  void  setBookName(String arg_bookName){
        this.book_name=arg_bookName;
    }
    public  void  setBoookPrice(double arg_bookPrice){
        this.book_price=arg_bookPrice;
    }
    public  void  setBookCount(int arg_bookCount){
        this.book_count=arg_bookCount;
    }

}
