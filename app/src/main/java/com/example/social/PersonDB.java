package com.example.social;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PersonDB extends SQLiteOpenHelper {
    private final static int _DBVersion = 1;
    private final static String _DBName = "personal_information.db";

    public PersonDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PersonDB(Context context){ super(context, _DBName, null, _DBVersion); }

    /** create table when there's no available table */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL =  "CREATE TABLE IF NOT EXISTS " + PersonalInformation.DATABASE_TABLE + "( "
                            + PersonalInformation.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + PersonalInformation.KEY_NA + " TEXT, "
                            + PersonalInformation.KEY_GR + " TEXT, "
                            + PersonalInformation.KEY_AB + " TEXT, "
                            + PersonalInformation.KEY_CO + " TEXT, "
                            + PersonalInformation.KEY_CI + " TEXT, "
                            + PersonalInformation.KEY_AG + " TEXT, "
                            + PersonalInformation.KEY_GE + " TEXT, "
                            + PersonalInformation.KEY_IN + " TEXT, "
                            + PersonalInformation.KEY_PE + " TEXT )";
        db.execSQL(SQL);
    }

    /** dump the old table and create a new one */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone
        db.execSQL("DROP TABLE IF EXISTS " + Account.DATABASE_TABLE);
        // Create tables again
        onCreate(db);
    }
}
