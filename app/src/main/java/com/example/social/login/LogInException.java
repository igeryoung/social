package com.example.social.login;
/*
*   deal exception general in login
 */
public class LogInException extends Exception {

    public enum ErrorType {account_blank,account_undefine , password_blank , password_error};
    /*
       account_undefine : unregister account
       password_error : password can't match account
     */
    private ErrorType error;

    public LogInException(ErrorType error) {
        this.error = error;
    }

    @Override
    public String getMessage(){
        switch (error){
            case account_blank:
                return "請輸入帳號";
            case password_blank:
                return "請輸入密碼";
            case password_error:
                return "密碼錯誤請再試一次";
            case account_undefine:
                return "查無此帳號";
        }
        return "";
    }
}
