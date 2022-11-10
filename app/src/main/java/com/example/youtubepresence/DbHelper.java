package com.example.youtubepresence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;

    //class table
    private static final String CLASS_TABLE_NAME = "CLASS_TABLE";
    public static final String C_ID = "_CID";
    public static final String CLASS_NAME_KEY = "CLASS_NAME";


    private static final String CREATE_CLASS_TABLE =
            "CREATE TABLE " + CLASS_TABLE_NAME + "( " +
                    C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    CLASS_NAME_KEY + " TEXT NOT NULL, " +

                    "UNIQUE (" + CLASS_NAME_KEY + ")" +
                    ");";

    private static final String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE_NAME;
    private static final String SELECT_CLASS_TABLE = "SELECT * FROM " + CLASS_TABLE_NAME;

    //student table

    private static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String S_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";
    public static final String STUDENT_SPECALITE_ANN = "SPECIALITEANNE";
    public static final String STUDENT_N_LIGNE = "N_LIGNE";





    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME +
                    "( " +
                    S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    C_ID + " INTEGER NOT NULL, " +
                    STUDENT_SPECALITE_ANN + " Text, " +
                    STUDENT_N_LIGNE + " TEXT, " +
                    STUDENT_NAME_KEY + " TEXT NOT NULL, " +
                    " FOREIGN KEY ( " + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "(" + C_ID + ")" +
                    ");";

    private static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;
    private static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;


    //STATUS TABLE

    private static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";



    private static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME +
                    "(" +
                    STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    S_ID + " INTEGER NOT NULL, " +
                    C_ID + " INTEGER NOT NULL, " +
                    DATE_KEY + " DATE NOT NULL, " +
                    STATUS_KEY + " TEXT NOT NULL, " +
                    " UNIQUE (" + S_ID + "," + DATE_KEY + ")," +
                    " FOREIGN KEY (" + S_ID + ") REFERENCES " + STUDENT_TABLE_NAME + "( " + S_ID + ")," +
                    " FOREIGN KEY (" + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "( " + C_ID + ")" +
                    ");";
    private static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE = "SELECT * FROM " + STATUS_TABLE_NAME;


    public DbHelper(@Nullable Context context) {
        super(context, "Attendance.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_CLASS_TABLE);
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void remouveStatusTable(){
         String DELETE_STATUS_TABLE = "DELETE FROM " + STATUS_TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase(); ;
        try {
            database.execSQL(DELETE_STATUS_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    long addClass(String className,String subjectName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY,className);


        return database.insert(CLASS_TABLE_NAME,null,values);
    }

    Cursor getClassTable(){
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(SELECT_CLASS_TABLE,null);
    }

    int deleteClass(long cid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(CLASS_TABLE_NAME,C_ID+"=?",new String[]{String.valueOf(cid)});
    }

    String getAllStudent(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        String number = null;
        Cursor cursor = database.rawQuery("SELECT COUNT( " +S_ID +") FROM " + STUDENT_TABLE_NAME + " WHERE " + C_ID + "=?", new String[]{String.valueOf(cid)});
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.moveToFirst()) {
                number = String.valueOf(cursor.getInt(0));
            } else {
                number = "0";
            }

        }
        return number;
    }

    long updateClass(long cid,String className,String subjectName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY,className);


        return database.update(CLASS_TABLE_NAME,values,C_ID+"=?",new String[]{String.valueOf(cid)});
    }

    long addStudent(long cid,String specialitéAnn,String n_ligne,String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID,cid);
        values.put(STUDENT_SPECALITE_ANN,specialitéAnn);
        values.put(STUDENT_N_LIGNE,n_ligne);
        values.put(STUDENT_NAME_KEY,name);

        return database.insert(STUDENT_TABLE_NAME,null,values);
    }

    Cursor getStudentTable(long cid){
        String sort="CAST ("+ STUDENT_N_LIGNE + " AS INTEGER)";

        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STUDENT_TABLE_NAME,null,C_ID+"=?",new String[]{String.valueOf(cid)},null,null, sort);
    }

    int deleteStudent(long sid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME,S_ID+"=?",new String[]{String.valueOf(sid)});
    }

    long updateStudent(long sid,String specialitéAnn,String n_ligne,String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY,name);
        values.put(STUDENT_SPECALITE_ANN,specialitéAnn);
        values.put(STUDENT_N_LIGNE,n_ligne);


        return database.update(STUDENT_TABLE_NAME,values,S_ID+"=?",new String[]{String.valueOf(sid)});
    }



    long addStatus(long sid,long cid,String date,String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_ID,sid);
        values.put(C_ID,cid);
        values.put(DATE_KEY,date);
        values.put(STATUS_KEY,status);
        //values.put(PRESENT_COUNT,present);
        //values.put(ABSENT_COUNT,absent);


        return database.insert(STATUS_TABLE_NAME,null,values);
    }

    long updateStatus(long sid,String date,String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_KEY,status);
        //values.put(PRESENT_COUNT,present);
        //values.put(ABSENT_COUNT,absent);


        String whereClause = DATE_KEY +"='"+date+"' AND "+S_ID+"="+sid;
        return database.update(STATUS_TABLE_NAME,values,whereClause,null);
    }

    String getStatus(long sid,String date){
        String status=null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = DATE_KEY +"='"+date+"' AND "+S_ID+"="+sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME,null,whereClause,null,null,null,null);
        if(cursor.moveToFirst())
            status = cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;
    }
    public ArrayList<String> getAllStatus(long sid){
        ArrayList<String> countPresence=new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = S_ID+"="+sid;

        //treja3 object mn naw3 cursor
        //la valeur de cursor in first -1
        Cursor cursor=database.rawQuery("SELECT * FROM " +STATUS_TABLE_NAME+" WHERE "+S_ID+"=?",new String[]{String.valueOf(sid)});
        if(cursor !=null && cursor.moveToFirst()){
            do{

                String status=cursor.getString(cursor.getColumnIndex(STATUS_KEY));



                countPresence.add(status);
            }
            while(cursor.moveToNext());

        }
        return countPresence;

    }
    String getAbsentCount(long sid){
        //String status=null;
        String absent;

        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_KEY +"=A"+ " AND " +S_ID+"="+sid;
        String SELECT_ABSENT_COUNT = "SELECT COUNT( " +STATUS_KEY+ ") FROM " + STATUS_TABLE_NAME +" WHERE " +whereClause;
        Cursor cursor = database.rawQuery(SELECT_ABSENT_COUNT,null);
        if(cursor.moveToFirst()) {
           absent= String.valueOf(cursor.getInt(0));
        }
        else{
            absent="0";
        }
        return absent;
    }
    String getPresentCount(long sid){
        //String status=null;
        String present;

        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_KEY +"= P"+ "AND" +S_ID+"="+sid;
        String SELECT_PRESENT_COUNT = "SELECT COUNT(*) FROM " + STATUS_TABLE_NAME +" WHERE " +whereClause;
        Cursor cursor = database.rawQuery(SELECT_PRESENT_COUNT,null);
        if(cursor.moveToFirst()) {
            present= String.valueOf(cursor.getInt(0));
        }
        else{
            present="0";
        }
        return present;
    }



    Cursor getDistinctMonths(long cid) {
        String sort="substr(" + DATE_KEY + ",4,7)";


        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STATUS_TABLE_NAME, new String[]{DATE_KEY}, C_ID + "=" + cid, null, "substr(" + DATE_KEY + ",4,7)", null,  "substr ( "+DATE_KEY+" ,6,10) || substr (" +DATE_KEY+" ,3,5) DESC");//01.04.2020
    }
    }
