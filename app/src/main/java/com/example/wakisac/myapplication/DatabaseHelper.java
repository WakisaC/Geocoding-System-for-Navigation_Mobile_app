package com.example.wakisac.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static  final String DATABASE_NAME = "Register.db";
    public  static  final String TABLE_NAME = "user";
      public  static  final String COL_1= "NAME";
    public  static  final String COL_2 = "PHONE_NUMER";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME +"(NAME TEXT,PHONE_NUMBER INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String name,int phone){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,phone);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    // get name from database
    public String getName(){
        String name ;
        String querry = "select NAME from TABLENAME where ID = 1";

        Cursor cursor = getReadableDatabase().rawQuery(querry,null);
        name = cursor.getString(0);
        return name;
    }
    // get phone from database
    public int gePhone(){
       int phone ;
        String querry = "select PHONE from TABLENAME where ID = 1";

        Cursor cursor = getReadableDatabase().rawQuery(querry,null);
        phone = cursor.getInt(0);
        return phone;
    }

}
