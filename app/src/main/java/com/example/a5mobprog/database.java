package com.example.a5mobprog;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class database extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String KEY_ID = "id";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PW = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DEPT = "department";



    public database(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String department, name, phone;
        phone = "0712435678";
        name = "HR USER";
        department = "HR";
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_DEPT +  department
                + KEY_PHONE +  phone
                + KEY_NAME +  name
                + KEY_PW + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String email, String password, String department, String name, String phone){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_EMAIL, email);
        cValues.put(KEY_PW, password);
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_DEPT, department);
        cValues.put(KEY_PHONE, phone);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    // Get User Details
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT email, password, phone, department, name FROM userdetails";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("email",cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PW)));
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("phone",cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            user.put("department",cursor.getString(cursor.getColumnIndex(KEY_DEPT)));
            userList.add(user);
        }
        return  userList;
    }
    // Get User Details based on userid
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT email, password FROM "+ TABLE_Users;
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_Users, new String[]{KEY_PW, KEY_EMAIL, KEY_DEPT, KEY_NAME}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("email",cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PW)));
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("phone",cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            user.put("department",cursor.getString(cursor.getColumnIndex(KEY_DEPT)));
            userList.add(user);
        }
        return  userList;
    }
    // Update User Details
    public int UpdateUserDetails(String email, String password, String department, String name, String phone, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_EMAIL, email);
        cVals.put(KEY_PW, password);
        cVals.put(KEY_NAME, name);
        cVals.put(KEY_DEPT, department);
        cVals.put(KEY_PHONE, phone);



        return db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
    }
}

