package com.example.social.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.social.Account;

public class AccountDB extends SQLiteOpenHelper {
    private final static int _DBVersion = 1;
    private final static String _DBName = "account.db";

    public AccountDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AccountDB(Context context){ super(context, _DBName, null, _DBVersion); }

    /** create table when there's no available table */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL =  "CREATE TABLE IF NOT EXISTS " + Account.DATABASE_TABLE + "( "
                            + Account.KEY_AC + " TEXT, "
                            + Account.KEY_PA + " TEXT )";
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
