package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 2;

    public TodoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ddl","initing database");
        db.execSQL(TodoContract.SQL_CREATE_NOTES);
        Log.d("ddl","inited database");
        //Cursor cursor=db.rawQuery("select "+TodoContract.TodoNote.COLUMN_DDL_DATE+" from table "+ TodoContract.TodoNote.TABLE_NAME,null);
        //Log.d("ddl",cursor.getColumnName(0));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    db.execSQL(TodoContract.SQL_ADD_PRIORITY_COLUMN);
                    break;
            }
        }
    }
}
