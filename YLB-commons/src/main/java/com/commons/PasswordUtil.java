package com.commons;

public class PasswordUtil {
    public static boolean passwordTrue(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,20})$";
        if(password.matches(regex)){
            return true;
        }else{
            return false;
        }
    }
}
