package com.hamonusers.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.ArrayList;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKS = "Books";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + "previewLink TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "subtitle TEXT,"
                + "authors TEXT,"
                + "publisher TEXT,"
                + "publishedDate TEXT,"
                + "description TEXT,"
                + "pageCount INTEGER,"
                + "thumbnail TEXT,"
                + "infoLink TEXT,"
                + "buyLink TEXT"
                + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }


    public boolean addBook(BookInfo book) {
        if (isBookSaved(book.getPreviewLink())) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("previewLink", book.getPreviewLink());
        values.put("title", book.getTitle());
        values.put("subtitle", book.getSubtitle());
        values.put("authors", String.join(", ", book.getAuthors()));
        values.put("publisher", book.getPublisher());
        values.put("publishedDate", book.getPublishedDate());
        values.put("description", book.getDescription());
        values.put("pageCount", book.getPageCount());
        values.put("thumbnail", book.getThumbnail());
        values.put("infoLink", book.getInfoLink());
        values.put("buyLink", book.getBuyLink());

        long result = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return result != -1;
    }

    public boolean isBookSaved(String previewLink) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS,
                new String[]{"previewLink"},
                "previewLink=?",
                new String[]{previewLink},
                null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    public ArrayList<BookInfo> getAllBooks() {
        ArrayList<BookInfo> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS, null);

        if (cursor.moveToFirst()) {
            do {
                BookInfo book = new BookInfo();
                book.setPreviewLink(cursor.getString(cursor.getColumnIndexOrThrow("previewLink")));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                book.setSubtitle(cursor.getString(cursor.getColumnIndexOrThrow("subtitle")));

                String authorsStr = cursor.getString(cursor.getColumnIndexOrThrow("authors"));
                ArrayList<String> authorsList = new ArrayList<>();
                if (authorsStr != null && !authorsStr.isEmpty()) {
                    for (String s : authorsStr.split(",")) {
                        authorsList.add(s.trim());
                    }
                }
                book.setAuthors(authorsList);

                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow("publisher")));
                book.setPublishedDate(cursor.getString(cursor.getColumnIndexOrThrow("publishedDate")));
                book.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                book.setPageCount(cursor.getInt(cursor.getColumnIndexOrThrow("pageCount")));
                book.setThumbnail(cursor.getString(cursor.getColumnIndexOrThrow("thumbnail")));
                book.setInfoLink(cursor.getString(cursor.getColumnIndexOrThrow("infoLink")));
                book.setBuyLink(cursor.getString(cursor.getColumnIndexOrThrow("buyLink")));

                books.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return books;
    }

    public void deleteBook(String previewLink) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, "previewLink=?", new String[]{previewLink});
        db.close();
    }

}
