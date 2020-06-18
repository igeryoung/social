package com.example.social;

import android.widget.TextView;

public class RegisterException extends Exception {
    public enum ErrorType {account_blank , password_blank , assure_error , assure_blank};
    private RegisterException.ErrorType error;
    public RegisterException(RegisterException.ErrorType error) {
        this.error = error;
    }
    @Override
    public String getMessage(){
        switch (error){
            case account_blank:
                return "請輸入帳號";
            case password_blank:
                return "請輸入密碼";
            case assure_blank:
                return "請再次輸入密碼";
            case assure_error:
                return "密碼錯誤請再試一次";
        }
        return "";
    }

}
