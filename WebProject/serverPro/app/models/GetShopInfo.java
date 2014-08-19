package models;

/**
 * Created by Administrator on 2014/8/17.
 */
public  class  GetShopInfo {
   public  int shopcartid;
    public int book_id;
    public String book_img;
    public  String book_name;
    public  double book_price;
    public  int   book_count;

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
