package uk.co.knorris.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "staff_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_STAFF = "staff";
    private static final String KEY_ID = "_id";
    private static final String KEY_FIRSTNAME_COLUMN = "FIRSTNAME_COLUMN";
    private static final String KEY_LASTNAME_COLUMN = "LASTNAME_COLUMN";
    private static final String KEY_OFFICE_NUMBER_COLUMN = "OFFICE_NUMBER_COLUMN";

    private static final String CREATE_DATABASE_TABLE_STAFF = "CREATE TABLE "
            + DATABASE_TABLE_STAFF + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FIRSTNAME_COLUMN + " TEXT,"
            + KEY_LASTNAME_COLUMN + " TEXT,"
            + KEY_OFFICE_NUMBER_COLUMN + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//DatabaseHelper

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_TABLE_STAFF);
    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE_STAFF + "'");
        onCreate(db);
    }//onUpgrade

    public void addStaffMember(String firstName, String lastName, String officeNumber) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_FIRSTNAME_COLUMN, firstName);
        newValues.put(KEY_LASTNAME_COLUMN, lastName);
        newValues.put(KEY_OFFICE_NUMBER_COLUMN, officeNumber);
        db.insert(DATABASE_TABLE_STAFF, null, newValues);

    }//addStaffMember

    public ArrayList<StaffMemberModel> getAllStaffMembers() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<StaffMemberModel> staffMemberModelArrayList = new ArrayList<StaffMemberModel>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_STAFF;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            StaffMemberModel staffMemberModel = new StaffMemberModel();
            staffMemberModel.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            staffMemberModel.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME_COLUMN)));
            staffMemberModel.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LASTNAME_COLUMN)));
            staffMemberModel.setOfficeNumber(cursor.getString(cursor.getColumnIndex(KEY_OFFICE_NUMBER_COLUMN)));
            staffMemberModelArrayList.add(staffMemberModel);
        }
        return staffMemberModelArrayList;

    }//getAllUsers

    public void updateStaffMember(int id, String firstName, String lastName, String officeNumber) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(KEY_FIRSTNAME_COLUMN, firstName);
        updatedValues.put(KEY_LASTNAME_COLUMN, lastName);
        updatedValues.put(KEY_OFFICE_NUMBER_COLUMN, officeNumber);
        String where = KEY_ID + " = ?";
        String whereArgs[] = new String[]{String.valueOf(id)};
        db.update(DATABASE_TABLE_STAFF, updatedValues, where, whereArgs);

    }//updateStaffMember

    public void deleteStaffMember(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_STAFF, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }//deleteStaffMember

}//DatabaseHelper class
