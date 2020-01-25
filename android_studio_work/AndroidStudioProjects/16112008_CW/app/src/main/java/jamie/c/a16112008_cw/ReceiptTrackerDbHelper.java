package jamie.c.a16112008_cw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


// When using the SQLiteOpenHelper class which contains useful APIs.
// The class manages potential long-running operations for creating
// and updating only when needed and not during app startup.

public class ReceiptTrackerDbHelper extends SQLiteOpenHelper {
    // If you change the database scehma, you must increment the database version.
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "ReceiptTracker.db";

    public ReceiptTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReceiptTrackerSchema.Expenses.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ReceiptTrackerSchema.Expenses.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addReceipt(Expense expense) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_ADDED, expense.getDateAdded());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_INCURRED, expense.getDateIssued());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_AMOUNT, expense.getAmount());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_VAT, expense.getVAT());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_TOTAL_AMOUNT, expense.getTotalamount());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_BODY, expense.getDescription());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_IMG, expense.getImage());

        db.insert(ReceiptTrackerSchema.Expenses.TABLE_NAME, null, values);
        db.close();
    }

    // TODO: create method to remove a given row by the id.
    public void removeReceipt(int ID) {
        // do a statement to find row where ID = _Id. Then delete the row
    }

    // TODO: create a method to update a row by the id.
    public void updateReceipt(int ID, String datePaid, double amount, boolean VAT,
                              double totalAmount, String body, String img, boolean claimed) {
        // do a statement to update the row where ID = _Id. Then update any of the fields.
    }

    public ArrayList<Expense> getAllReceipts() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Expense> Receipts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ReceiptTrackerSchema.Expenses.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()) {
            Expense Receipt = new Expense();
            Receipt.setID(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses._ID)));
            Receipt.setDateAdded(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_ADDED)));
            Receipt.setDateIssued(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_INCURRED)));
            Receipt.setDatePaid(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_PAID)));
            Receipt.setPaid(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_PAID)));
            Receipt.setAmount(cursor.getDouble(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_AMOUNT)));
            Receipt.setVAT(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_VAT)));
            Receipt.setTotalAmount(cursor.getDouble(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_TOTAL_AMOUNT)));
            Receipt.setDescription(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_BODY)));
            Receipt.setImage(cursor.getBlob(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_IMG)));
            Receipts.add(Receipt);
        }

        return Receipts;
    }

    public boolean deleteReceipt(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int result = db.delete(ReceiptTrackerSchema.Expenses.TABLE_NAME,
                    ReceiptTrackerSchema.Expenses._ID + " = ?",
                    new String[] { String.valueOf(id)});
            if(result > 0) {
                return true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;

    }

    public int updateReceipt(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_ADDED, expense.getDateAdded());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_INCURRED, expense.getDateIssued());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_PAID, expense.getDatePaid());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_PAID, expense.getPaid());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_AMOUNT, expense.getAmount());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_VAT, expense.getVAT());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_TOTAL_AMOUNT, expense.getTotalamount());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_BODY, expense.getDescription());
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_IMG, expense.getImage());
        // return the integer value to confirm if the receipt has been updated or not.
        return db.update(ReceiptTrackerSchema.Expenses.TABLE_NAME, values,
                ReceiptTrackerSchema.Expenses._ID + " = ?",
                new String[] { String.valueOf(expense.getID())});
    }

    public int updatePaid(String date, int paid, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_PAID, date);
        values.put(ReceiptTrackerSchema.Expenses.COLUMN_NAME_PAID, paid);

        return db.update(ReceiptTrackerSchema.Expenses.TABLE_NAME, values,
                ReceiptTrackerSchema.Expenses._ID + " = ?",
                new String[] { String.valueOf(id)});


    }

    public Expense getReceipt(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Expense expense = new Expense();

        String selectQuery = "SELECT * FROM " + ReceiptTrackerSchema.Expenses.TABLE_NAME + " WHERE _ID = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()) {
            expense.setID(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses._ID)));
            expense.setDateAdded(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_ADDED)));
            expense.setDateIssued(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_INCURRED)));
            expense.setDatePaid(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_DATE_PAID)));
            expense.setPaid(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_PAID)));
            expense.setAmount(cursor.getDouble(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_AMOUNT)));
            expense.setVAT(cursor.getInt(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_VAT)));
            expense.setTotalAmount(cursor.getDouble(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_TOTAL_AMOUNT)));
            expense.setDescription(cursor.getString(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_BODY)));
            expense.setImage(cursor.getBlob(cursor.getColumnIndex(
                    ReceiptTrackerSchema.Expenses.COLUMN_NAME_IMG)));
        }
        db.close();
        return expense;
    }

    //public void getAllRecords
}
