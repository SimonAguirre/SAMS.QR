package com.sams.samsqr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "ATTENDANCE_LOG_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";
    public static final String COLUMN_TIMESTAMP = "TEXT";
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
                COLUMN_TIMESTAMP + " TIMESTAMP)";
        sqLiteDatabase.execSQL(CREATE_TABLE_STATEMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(AttendanceLog attendanceLog){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME, attendanceLog.getFullname());
        cv.put(COLUMN_TIMESTAMP, attendanceLog.getTimestamp().toString());

        long insert = db.insert(TABLE_NAME, null, cv);

        if (insert <= -1){
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
                Timestamp timestamp = Timestamp.valueOf(cursor.getString(2));

                AttendanceLog attendanceLog = new AttendanceLog(attendanceID,timestamp,name);
                returnList.add(attendanceLog);
            }while (cursor.moveToNext());
        } else {
            Toast.makeText(CONTEXT, "Empty",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<AttendanceLog> getAll(String date1, String date2){ //format YYYY-MM-DD
        List<AttendanceLog> returnList = new ArrayList<>();
        //get all data from the database
        String QUERY_STATEMENT = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_TIMESTAMP+" BETWEEN '"+date1+"' AND '"+date2+"' ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STATEMENT,null);
        if (cursor.moveToFirst()){
            //loop through the result
            do{
                int attendanceID = cursor.getInt(0);
                String name = cursor.getString(1);
                Timestamp timestamp = Timestamp.valueOf(cursor.getString(2));

                AttendanceLog attendanceLog = new AttendanceLog(attendanceID,timestamp,name);
                returnList.add(attendanceLog);
            }while (cursor.moveToNext());
        } else {
            Toast.makeText(CONTEXT, "Empty",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean addOne(Student student){

        return false;
    }

    public boolean export_csv(String time1, String time2) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(CONTEXT,"Permission to write on storage denied",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            //We use the Download directory for saving our .csv file.
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file;
            PrintWriter printWriter = null;
            try
            {
                String today = new SimpleDateFormat("MMM_dd_yyyy", Locale.getDefault()).format(new Date());
                file = new File(exportDir, today+" Attendance.csv");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */
                SQLiteDatabase db = this.getReadableDatabase(); //open the database for reading

                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 */
                Cursor curCSV = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_TIMESTAMP+" BETWEEN '"+time1+"' AND '"+time2+"' ;", null);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter.println("ATTENDANCE ID,STUDENT NAME, TIME IN");


                while (curCSV.moveToNext()){

                    int attendanceID = curCSV.getInt(0);
                    String name = curCSV.getString(1);
                    Timestamp timestamp = Timestamp.valueOf(curCSV.getString(2));
                    String pattern = "MM-dd-yyyy hh:mm:ss a";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String time = simpleDateFormat.format(timestamp);
                    /**Create the line to write in the .csv file.
                     * We need a String where values are comma separated.
                     * The field date (Long) is formatted in a readable text. The amount field
                     * is converted into String.
                     */
                    String record = attendanceID + "," + name + "," + time;
                    printWriter.println(record); //write the record in the .csv file
                }

                curCSV.close();
                db.close();
            }

            catch(Exception exc) {
                //if there are any exceptions, return false
                return false;
            }
            finally {
                if(printWriter != null) printWriter.close();
            }

            //If there are no errors, return true.
            return true;
        }
    }
}
