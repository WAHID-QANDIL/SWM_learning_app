package com.example.sqlliteproject.models.Services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlliteproject.models.SqlLite.DatabaseConnection;
import com.example.sqlliteproject.models.SqlLite.DatabaseOperations;
import com.example.sqlliteproject.models.Tables.ToDoList;

import java.util.ArrayList;
import java.util.Date;

public class ToDoService implements DatabaseOperations<ToDoList> {
    private DatabaseConnection dbHelper;
    Context con;
    public ToDoService(Context context) {
        dbHelper = new DatabaseConnection(context);
        this.con=context;
    }



    @Override
    public String addItem(ToDoList toDoList) {

        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("taskName", toDoList.getTaskName());
        values.put("taskDescription", toDoList.getTaskDescription());
        values.put("DurationTime", toDoList.getDurationTime());
        Date date=new Date();
        values.put("taskDate", date.toString());
        values.put("user_id", toDoList.getUser_id());

        //forging  key

        values.put("user_email", toDoList.getUser_email());
        db1.insert("ToDoList", null,values);
        db1.close();

        //adding WeeklyAchivement if status is true


//        WeeklyAchievementsService w=new WeeklyAchievementsService(con);
//        WeeklyAchievements week =new WeeklyAchievements(1,toDoList.getDurationTime(),toDoList.getUser_id(),toDoList.getUser_email());
//        w.addItem(week);


        return "good";
    }


    @Override
    public ArrayList<ToDoList> getAllItems() {

        ArrayList<ToDoList> arrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor1 = db.rawQuery("select * from ToDoList",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()){
            arrayList.add(new ToDoList(
                    cursor1.getLong(0),
                    cursor1.getString(1),
                    cursor1.getString(2),
                    cursor1.getInt(3),
                    cursor1.getString(4),

                    cursor1.getInt(5),
                    cursor1.getString(6)));

            cursor1.moveToNext();
        }
        db.close();
        return arrayList;
    }

    @Override
    public ArrayList<ToDoList> getDataByEmail(String email) {

        ArrayList<ToDoList> selectedLists=new ArrayList<ToDoList>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ToDoList  WHERE user_email = ?", new String[]{email});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            selectedLists.add(new ToDoList(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6)));

            cursor.moveToNext();
        }

        db.close();
        return selectedLists;
    }
@Override
    public ArrayList<ToDoList> getDataByID(long ID){
        ArrayList<ToDoList> AllList = getAllItems();
        ArrayList<ToDoList> selectedList=new ArrayList<ToDoList>();
        boolean ch=false;
        for(ToDoList list :AllList){
            if(ID == list.getUser_id()){
                selectedList.add(list);
                ch=true;
            }
        }
        if(ch) {
            return selectedList;
        }else {
            return null;
        }
    }

    @Override
    public void updateUserEmail(String email, String newEmail) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_email", newEmail);
        db.update("ToDoList", values, "user_email" + " = ?", new String[]{email});
        db.close();
    }


    /**
     *
     * delete
     */

//    public void deleteLastToDoListAddedById(long id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.delete("ToDoList", "",null);
//        db.execSQL("DELETE FROM ToDoList WHERE user_id = " + id + " AND taskId = (SELECT MAX(taskId) FROM ToDoList WHERE user_id = " + id + ")");
//        db.close();
//    }

    @Override
    public void deleteItem(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ToDoList", "user_email" + " = ?", new String[]{email}) ;
        db.close();

        //Another way
//        String deleteQuery = "DELETE FROM ToDoList WHERE user_id = " + id +"";
//        db.execSQL(deleteQuery);


    }
    public void deleteLastToDoListAddedById(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM ToDoList WHERE user_id = " + id + " AND taskId = (SELECT MAX(taskId) FROM ToDoList WHERE user_id = " + id + ")";
        db.execSQL(deleteQuery);
        db.close();
    }
    public void deleteLastToDoListAddedByEmail(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM ToDoList WHERE user_email = ? AND taskId = (SELECT MAX(taskId) FROM ToDoList WHERE user_email = ?)";
        db.execSQL(deleteQuery, new String[]{email, email});
        db.close();
    }

    public void deleteToDoListById(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ToDoList", "taskId = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    ///     update ToDoList
    //good


    public void updateToDoListTaskName(String TaskName, String newTaskName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taskName", newTaskName);
        db.update("ToDoList", values, "taskName" + " = ?", new String[]{TaskName});
        db.close();
    }
    public void updateToDoListTaskDescription(String TaskDescription, String newTaskDescription) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taskDescription", newTaskDescription);
        db.update("ToDoList", values, "taskDescription" + " = ?", new String[]{TaskDescription});
        db.close();
    }

}
