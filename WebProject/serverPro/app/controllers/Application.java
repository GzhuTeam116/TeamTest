package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

//import models.*;

public class Application extends Controller {

    public static void index() {
        redirect("../../public/index.html");
//        render();
    }

    public static void login(){
        String userName = params.get("userName");
        renderText("userName receive: " + userName);
    }
}