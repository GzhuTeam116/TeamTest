package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render("Application/admin/login.html");
    }
    public static void bye(){
        render("Application/admin/login.html");
    }

}