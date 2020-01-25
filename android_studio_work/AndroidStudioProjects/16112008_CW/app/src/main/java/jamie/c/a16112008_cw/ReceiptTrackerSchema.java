package jamie.c.a16112008_cw;

import android.provider.BaseColumns;

public final class ReceiptTrackerSchema {
    private ReceiptTrackerSchema() {}

    // By implemnting the BaseColumns interface the class inherits
    // a primary key field callsed _ID. Needed for some classes.
    public static class Expenses implements BaseColumns {
        public static final String TABLE_NAME = "tbl_expenses";
        // we can skip ID as this will be automatically generated
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_DATE_INCURRED = "date_incurred";
        public static final String COLUMN_NAME_DATE_PAID = "date_paid";
        public static final String COLUMN_NAME_PAID = "paid";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_VAT = "VAT";
        public static final String COLUMN_NAME_TOTAL_AMOUNT = "total_amount";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_IMG = "img";


        // SQLite is Dyanmically typed. Each column can contain an affliate
        // type e.g. TEXT, NUM, INT, REAL, "" (a.k.a "NONE").
        // Since the type is dynamic the column is used to determine
        // the affinity of the column only. But any data can be stored ina  column.
        // ref: https://www.sqlite.org/lang_createtable.html

        // We will use 0 and 1 for false, and true respectively for boolean operators.
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Expenses.TABLE_NAME + " (" +
                        Expenses._ID + " INTEGER PRIMARY KEY, " +
                        Expenses.COLUMN_NAME_DATE_ADDED + " TEXT, " +
                        Expenses.COLUMN_NAME_DATE_INCURRED + " TEXT, " +
                        Expenses.COLUMN_NAME_DATE_PAID + " TEXT, " +
                        Expenses.COLUMN_NAME_PAID + " INT, " +
                        Expenses.COLUMN_NAME_AMOUNT + " NUM," +
                        Expenses.COLUMN_NAME_VAT + " INT, " +
                        Expenses.COLUMN_NAME_TOTAL_AMOUNT + " NUM, " +
                        Expenses.COLUMN_NAME_BODY + " TEXT, " +
                        Expenses.COLUMN_NAME_IMG + " BLOB)";

        // deletes the table from the database.
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Expenses.TABLE_NAME;
    }
}
