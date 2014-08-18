package models;

import java.util.List;

/**
 * Created by Administrator on 2014/8/17.
 */
public class GetShopArr {
    String orderform_num;
    double orderform_price;
    List shopcart;

    public  void  setOrderNum(String arg_orderNum){
        this.orderform_num=arg_orderNum;

    }

    public  void  setOrderPrice(double arg_orderPrice){
        this.orderform_price=arg_orderPrice;

    }
    public  void  setShopcart(List arg_shopInfo){
       this.shopcart=arg_shopInfo;
    }


}
