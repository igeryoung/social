package com.example.social.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.social.Account;

public class AccountGetData {
    private AccountDB dbservice;

    public AccountGetData(Context context){ dbservice = new AccountDB(context); }

    /** return true if there's a same account, else return false*/
    public boolean checkIfAccountExist(String account){
        SQLiteDatabase db = dbservice.getReadableDatabase();
        boolean ret;
        Cursor cursor = db.rawQuery("select * from " + Account.DATABASE_TABLE + " where " + Account.KEY_AC +
                                    " = ?", new String[]{account});
        if(cursor.getCount() != 1)
            ret =  false;
        else
            ret =  true;
        cursor.close();
        db.close();
        return ret;
    }

    public boolean checkPassword(String account, String password){
        SQLiteDatabase db = dbservice.getReadableDatabase();
        boolean ret;
        Cursor cursor = db.rawQuery("select * from " + Account.DATABASE_TABLE + " where " + Account.KEY_AC +
                " = ?", new String[]{account});
        if(cursor.getCount() != 1)
            ret =  false;
        else{
            if(cursor.moveToFirst()){
                if(cursor.getString(cursor.getColumnIndex(Account.KEY_PA)).compareTo(password) == 0)
                    ret = true;
                else
                    ret = false;
            }
            else
                ret = false;
        }
        cursor.close();
        db.close();
        return ret;
    }

    public void insert(String account, String password){
        SQLiteDatabase db = dbservice.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Account.KEY_AC, account);
        values.put(Account.KEY_PA, password);
        db.insert(Account.DATABASE_TABLE, null, values);
        db.close();
    }

    /** you should check if there's any account by yourself*/
    public void changePaassword(String account, String password){
        SQLiteDatabase db = dbservice.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Account.KEY_PA, password);
        db.update(Account.DATABASE_TABLE, values, Account.KEY_AC + "=?", new String[]{account});
        db.close();
    }
}
