package controllers;

import org.h2.util.Task;
import play.mvc.*;
import play.mvc.Controller;
@With(Secure.class)
public class Application extends Controller {

    public static void index() {
           render();
    }

//    public static Result login() {
//
//        return ok(
//                login.render()
//        );
//    }


}



