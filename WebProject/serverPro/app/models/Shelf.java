package models;

/**
 * Created by jance on 2014/8/9.
 */
public class Shelf {
    public Integer id;
    public  String name;
    public Shelf(){
    }
    public  Shelf(Integer arg_id, String arg_name){
        this.id=arg_id;
        this.name=arg_name;
    }
    public  void  setId(Integer arg_id){
        this.id=arg_id;
    }
    public  void  setName(String arg_name){
        this.name=arg_name;
    }
}
