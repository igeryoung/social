package com.example.social;

public class Account {
    public static final String DATABASE_TABLE = "ACCOUNT_TABLE";

    public static final String KEY_AC = "account";
    public static final String KEY_PA = "password";

    private String account;
    private String password;

    public Account(){
        //Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Account(String account, String password){
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account){ this.account = account; }

    public String getAccount() { return this.account; }

    public void setPassword(String password){ this.password = password; }

    public String getPassword(){ return this.password; }
}
