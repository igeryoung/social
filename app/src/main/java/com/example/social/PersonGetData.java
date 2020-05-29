package com.example.social;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonGetData {
    private PersonDB dbservice;

    public PersonGetData(Context context){ dbservice = new PersonDB(context); }

    public int insert(PersonalInformation PI){
        SQLiteDatabase db = dbservice.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonalInformation.KEY_NA, PI.getName());
        values.put(PersonalInformation.KEY_GR, PI.getGraph());
        values.put(PersonalInformation.KEY_AB, PI.getAbout());
        values.put(PersonalInformation.KEY_CO, PI.getCollege());
        values.put(PersonalInformation.KEY_CI, PI.getCity());
        values.put(PersonalInformation.KEY_AG, PI.getAge());
        values.put(PersonalInformation.KEY_GE, PI.getGender());
        values.put(PersonalInformation.KEY_IN, PI.getInterest());
        values.put(PersonalInformation.KEY_PE, PI.getPersonality());

        long Order_Id = db.insert(PersonalInformation.DATABASE_TABLE, null, values);
        db.close(); // Closing database connection
        return (int) Order_Id;
    }

    public PersonalInformation getById(int id){
        SQLiteDatabase db = dbservice.getReadableDatabase();
        PersonalInformation returnPI;
        Cursor cursor =db.rawQuery("select * from " + PersonalInformation.DATABASE_TABLE + " where id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            returnPI = new PersonalInformation(cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_ID)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_NA)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_GR)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_AB)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_CO)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_CI)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_AG)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_GE)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_IN)),
                                                cursor.getString(cursor.getColumnIndex(PersonalInformation.KEY_PE))
                                                );
        }
        else
            returnPI = null;
        cursor.close();
        db.close();
        return returnPI;
    }

    /** call getById() to get the old PersonalInformation and modify it, then call this funciton*/
    public void changePI(PersonalInformation PI){
        SQLiteDatabase db = dbservice.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonalInformation.KEY_NA, PI.getName());
        values.put(PersonalInformation.KEY_GR, PI.getGraph());
        values.put(PersonalInformation.KEY_AB, PI.getAbout());
        values.put(PersonalInformation.KEY_CO, PI.getCollege());
        values.put(PersonalInformation.KEY_CI, PI.getCity());
        values.put(PersonalInformation.KEY_AG, PI.getAge());
        values.put(PersonalInformation.KEY_GE, PI.getGender());
        values.put(PersonalInformation.KEY_IN, PI.getInterest());
        values.put(PersonalInformation.KEY_PE, PI.getPersonality());
        db.update(PersonalInformation.DATABASE_TABLE, values, "_id=?", new String[]{PI.getId()});
        db.close();
    }
}
