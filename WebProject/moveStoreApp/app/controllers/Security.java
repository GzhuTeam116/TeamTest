package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
//        return true;
        return User.connect(username, password) != null;
    }


}