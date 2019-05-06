package com.example.wakisac.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class   DatabaseHelper extends SQLiteOpenHelper {

    public  static  final String DATABASE_NAME = "UserData.db";
    public  static  final String TABLE_NAME = "users";
     public  static  final String COL_1 = "NAME";
    public  static  final String COL_2= "PHONE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME +"(NAME TEXT,PHONE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void deleteCreateTable(String tableName){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL(" DROP TABLE IF EXISTS "+tableName);
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


    public int delete(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ? ",new String[]{id});
    }

    //method to check if any user exists in database

    public boolean user_exists(){
        int k=0;
        boolean user_existence;
        String querry = "select count(*) from users";

        Cursor cursor = getReadableDatabase().rawQuery(querry,null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            k = cursor.getInt(0);

            }
        if(k>0)
            return true;
        else
            return false;

    }


}
