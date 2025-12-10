package com.example.alfappcomp710;

public class SignUpValidator {

    public static boolean validateEmail(String input){
        String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean output;
        if(input.isEmpty()){
            output = false;
        }else if(!input.matches(emailPattern)){
            output = false;
        }else{
            output = true;
        }
        return output;
    }

    public static  boolean validateUserName(String input){
        String noWhiteSpace ="\\A\\w{4,20}\\z";
        boolean output;
        if(input.isEmpty()){
            output  = false;
        }else if(input.length() >= 20){
            output  =  false;
        }else if(!input.matches(noWhiteSpace)){
            output  =  false;
        }else{
            output  =  true;
        }
        return output;
    }

    public static boolean validatePhoneNum(String input){
        boolean output;
        if(input.isEmpty()){
            output = false;
        }else if(input.length() != 10){
            output = false;
        }else{
            output = true;
        }

        return output;
    }

    public static boolean validatePassword(String input){
        boolean output;
        String passVal = "^" +
                //"(?=.*[0-9])"+
                //"(?=.*[a-z])"+
                //"(?=.*[A-Z])"+
                "(?=.*[a-zA-Z])"+ //any Letter
                "(?=.*[@#$%&^+-+])"+ //at least one Special character
                "(?=\\S+$)"+ //no whitespace
                ".{4,}"+
                "$";
        if(input.isEmpty()){
            output = false;
        }else if(!input.matches(passVal)){
            output = false;
        }else{
            output = true;
        }
        return output;
    }

}
