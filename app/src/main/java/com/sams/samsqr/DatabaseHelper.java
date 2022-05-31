package com.sams.samsqr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "ATTENDANCE_LOG_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";
    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    public static Context CONTEXT;
    public static String DATABASE_NAME;

    public DatabaseHelper(@Nullable Context context, @Nullable String database_name) {
        super(context, database_name, null, 1);
        this.DATABASE_NAME = database_name;
        this.CONTEXT = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(CREATE_TABLE_STATEMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(AttendanceLog attendanceLog){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME, attendanceLog.getFullname());
        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<AttendanceLog> getAll(){
        List<AttendanceLog> returnList = new ArrayList<>();
        //get all data from the database
        String QUERY_STATEMENT = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STATEMENT,null);
        if (cursor.moveToFirst()){
            //loop through the result
            do{
                int attendanceID = cursor.getInt(0);
                String name = cursor.getString(1);
                int timestamp = cursor.getInt(2);

                AttendanceLog attendanceLog = new AttendanceLog(attendanceID,timestamp,name);
                returnList.add(attendanceLog);
            }while (cursor.moveToNext());
        } else {
            Toast.makeText(CONTEXT, "Empty Database!",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<AttendanceLog> getAll(int time1, int time2){
        List<AttendanceLog> returnList = new ArrayList<>();
        //get all data from the database
        String QUERY_STATEMENT = "SELECT "+COLUMN_TIMESTAMP+" FROM "+TABLE_NAME+" WHERE "+COLUMN_TIMESTAMP+"  BETWEEN "+time1+" AND "+time2+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STATEMENT,null);
        if (cursor.moveToFirst()){
            //loop through the result
            do{
                int attendanceID = cursor.getInt(0);
                String name = cursor.getString(1);
                int timestamp = cursor.getInt(2);

                AttendanceLog attendanceLog = new AttendanceLog(attendanceID,timestamp,name);
                returnList.add(attendanceLog);
            }while (cursor.moveToNext());
        } else {
            Toast.makeText(CONTEXT, "Empty Database!",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(CONTEXT,cursor.getInt(2),Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean addOne(Student student){

        return false;
    }
}
