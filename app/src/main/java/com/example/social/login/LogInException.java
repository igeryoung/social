package com.example.social.login;

public class LogInException extends Exception {
    public enum ErrorType {account_blank,account_undefine , password_blank , password_error};
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
