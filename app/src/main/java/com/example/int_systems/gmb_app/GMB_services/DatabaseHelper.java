package com.example.int_systems.gmb_app.GMB_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GMB.db";
    public static final String TABLE_NAME = "Driver";
    public static final String COL_2 = "farmerID";
    public static  final String COL_3 ="Status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT,farmerID TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String farmerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,farmerID);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDriverID() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select status from"+TABLE_NAME,null);
        return res;
    }
    public void updateMethod(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Status","true");
        db.update(TABLE_NAME, cv,null, null);


    }
    public void updateFalse(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Status","false");
        db.update(TABLE_NAME, cv,null, null);


    }
}


