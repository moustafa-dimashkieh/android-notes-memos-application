package com.example.noteapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "MemoDB";
    private static final String TABLE_NAME = "memos";
    private static int COUNTER = 0;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "Create table IF NOT Exists " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, title TEXT, date TEXT, content TEXT)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addMemo(Memo memo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", COUNTER++);
        values.put("title", memo.getTitle());
        values.put("date", memo.getDate());
        values.put("content", memo.getContent());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    void editMemo(int ID, String title, String date, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("date", date);
        values.put("content", content);
        db.update(TABLE_NAME, values, "ID="+ID, null);
    }

    void deleteMemo(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=" + ID, null);
    }

    public Memo[] getMemos(){
        List<Memo> memoList = new ArrayList<Memo>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                Memo myMemo = new Memo();
                myMemo.setID(cursor.getInt(0));
                myMemo.setTitle(cursor.getString(1));
                myMemo.setDate(cursor.getString(2));
                myMemo.setContent(cursor.getString(3));
                memoList.add(myMemo);
            }

            while(cursor.moveToNext());

        }

        Memo[] memoArray = new Memo[memoList.size()];
        memoList.toArray(memoArray);
        return memoArray;
    }
}
