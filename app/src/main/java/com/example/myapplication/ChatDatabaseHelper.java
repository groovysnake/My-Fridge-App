package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper
{

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "KEY_ID";
    public static final String COLUMN_ITEM = "KEY_MESSAGE";

    private static final String DATABASE_NAME = "items.db";
    private static final int VERSION_NUM = 2;

    // Database creation statement saved as a string
    private static final String DATABASE_CREATE= "create table "
            + TABLE_ITEMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ITEM
            + " text not null);";

    public ChatDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Downgrading database from version " +  newVersion + " to "
                        + oldVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}
