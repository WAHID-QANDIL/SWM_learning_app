package com.example.sqlliteproject.models.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    public static final String DatabaseName="qevq.db";
    public DatabaseConnection(Context con1){
        super(con1,DatabaseName,null,4);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(userId integer primary key autoincrement ,userFname text,userLname text,userAge integer ,userGender text,userEmail text unique not null ,userPassword text )");
        db.execSQL("create table ToDoList(taskId integer primary key autoincrement, taskName text, taskDescription text, DurationTime int, taskDate text,user_id INTEGER,user_email text, FOREIGN KEY(user_id) REFERENCES users(userId))");
   /*status boolean not null default 0 ,*/
        db.execSQL("create table Notes(noteId integer primary key autoincrement, noteTitle text, noteDescription text,noteDate text,user_id INTEGER,user_email text, FOREIGN KEY(user_id) REFERENCES users(userId))");
        db.execSQL("create table WeeklyAchievements(weeklyId integer primary key autoincrement, numberOfTasks integer,totalDurationTime integer,user_id INTEGER,user_email text, FOREIGN KEY(user_id) REFERENCES users(userId))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table IF EXISTS users");
        db.execSQL("DROP table IF EXISTS ToDoList");
        db.execSQL("DROP table IF EXISTS Notes");
        db.execSQL("DROP table IF EXISTS WeeklyAchievements");
        onCreate(db);
    }


}
