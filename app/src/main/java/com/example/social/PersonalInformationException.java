package com.example.social;

public class PersonalInformationException extends Exception {
    public enum ErrorType {image_blank, name_blank, gender_blank, age_blank, college_blank, city_blank, about_blank, personality_blank, interest_blank};
    private PersonalInformationException.ErrorType error;
    public PersonalInformationException(PersonalInformationException.ErrorType error){ this.error = error; }
    @Override    public String getMessage(){
        switch (error){
            case image_blank:
                return "請選取照片";
            case name_blank:
                return "請輸入密碼";
            case gender_blank:
                return "請輸入性別";
            case age_blank:
                return "請輸入年紀";
            case college_blank:
                return "請輸入大學";
            case city_blank:
                return "請輸入城市";
            case about_blank:
                return "請輸入關於自己";
            case personality_blank:
                return "請輸入個性";
            case interest_blank:
                return "請輸入興趣";
        }
        return "";
    }
}
