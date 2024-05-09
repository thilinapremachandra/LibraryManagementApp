package com.example.library_app_2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "LDB";
    private static final int DB_VERSION = 1;
    private static final String BOOK_TABLE = "newbook";


    // Book table constants
    public static final String BOOK_ID = "bid";
    public static final String BOOK_NAME = "bname";
    public static final String BOOK_AUTHOR = "bauthor";
    public static final String BOOK_PUBLICATION = "bpublication";
    public static final String BOOK_QUANTITY = "bquantity";



    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createBookTableQuery = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE + "("
                + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOK_NAME + " TEXT, "
                + BOOK_AUTHOR + " TEXT, "
                + BOOK_PUBLICATION + " TEXT, "
                + BOOK_QUANTITY + " INTEGER"
                + ")";
        db.execSQL(createBookTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        onCreate(db);
    }

    public String getBookDetails(String detailType) {
        switch (detailType) {
            case "BOOK_ID":
                return BOOK_ID;
            case "BOOK_NAME":
                return BOOK_NAME;
            case "BOOK_AUTHOR":
                return BOOK_AUTHOR;
            case "BOOK_PUBLICATION":
                return BOOK_PUBLICATION;
            case "BOOK_QUANTITY":
                return BOOK_QUANTITY;
            default:
                return null;
        }
    }


    public void insertBookDetails(String book_name, String book_author, String book_publication, int book_quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book_name);
        values.put(BOOK_AUTHOR, book_author);
        values.put(BOOK_PUBLICATION, book_publication);
        values.put(BOOK_QUANTITY, book_quantity);

        long newRowId = db.insert(BOOK_TABLE, null, values);
        if (newRowId != -1) {
            Log.d("DbHelper", "Book inserted successfully");
        } else {
            Log.e("DbHelper", "Error inserting book");
        }
        db.close();
    }

    public void updateBookDetails(int bookId, String book_name, String book_author, String book_publication, int book_quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book_name);
        values.put(BOOK_AUTHOR, book_author);
        values.put(BOOK_PUBLICATION, book_publication);
        values.put(BOOK_QUANTITY, book_quantity);

        String selection = BOOK_ID + "=?";
        String[] selectionArgs = {String.valueOf(bookId)};

        int rowsUpdated = db.update(BOOK_TABLE, values, selection, selectionArgs);
        if (rowsUpdated > 0) {
            Log.d("DbHelper", "Book updated successfully");
        } else {
            Log.e("DbHelper", "Error updating book");
        }
        db.close();
    }

    public void deleteBook(int bookId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = BOOK_ID + "=?";
        String[] selectionArgs = {String.valueOf(bookId)};

        int rowsDeleted = db.delete(BOOK_TABLE, selection, selectionArgs);
        if (rowsDeleted > 0) {
            Log.d("DbHelper", "Book deleted successfully");
        } else {
            Log.e("DbHelper", "Error deleting book");
        }
        db.close();
    }


    public Cursor getBookByName(String book_name){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {BOOK_ID,BOOK_NAME, BOOK_AUTHOR,BOOK_PUBLICATION,BOOK_QUANTITY};
        String selection = BOOK_NAME + "=?";
        String[] selectionArgs = {book_name};
        return db.query(BOOK_TABLE, projection, selection, selectionArgs, null, null, null);
    }

    public Cursor getAllBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + BOOK_TABLE;
        return db.rawQuery(selectQuery, null);
    }

}



