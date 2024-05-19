package Database_connectivity.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlliteproject.models.SqlLite.DatabaseConnection;
import com.example.sqlliteproject.models.SqlLite.DatabaseOperations;
import com.example.sqlliteproject.models.Tables.Notes;

import java.util.ArrayList;
import java.util.Date;

public class NotesService  implements DatabaseOperations<Notes> {

    private DatabaseConnection dbHelper;
    public NotesService(Context context) {
        dbHelper = new DatabaseConnection(context);
    }



    @Override
    public String addItem(Notes note) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("noteTitle", note.getNoteTitle());
        values.put("noteDescription", note.getNoteDescription());
        Date date=new Date();
        values.put("noteDate", date.toString());

        //forging  key

        values.put("user_id", note.getUser_id());
        values.put("user_email", note.getUser_email());

        long m= db.insert("Notes", null, values);
        db.close();

       if(m == -1){
           return "error happened ";
       }else {
           return "save good";
       }

    }



    @Override
    public ArrayList<Notes> getAllItems() {

        ArrayList<Notes> arrayList = new ArrayList<Notes>();

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery("select * from Notes",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()){
            arrayList.add(new Notes(
                    cursor1.getLong(0),
                    cursor1.getString(1),
                    cursor1.getString(2),
                    cursor1.getString(3),
                    cursor1.getLong(4),
                    cursor1.getString(5)));

            cursor1.moveToNext();
        }
        db1.close();
        return arrayList;
    }

    @Override
    public ArrayList<Notes> getDataByEmail(String email) {

      ArrayList<Notes> selectedNote=new ArrayList<Notes>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Notes  WHERE user_email = ?", new String[]{email});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            selectedNote.add(new Notes(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4),
                    cursor.getString(5)));

            cursor.moveToNext();
        }

             db.close();
            return selectedNote;

    }

    @Override
    public ArrayList<Notes> getDataByID(long ID) {

        ArrayList<Notes> AllNotes = getAllItems();
        ArrayList<Notes> selectedNote=new ArrayList<Notes>();
        boolean ch=false;
        for(Notes note : AllNotes){
            if(ID == note.getUser_id()){
                selectedNote.add(note);
                ch=true;
            }
        }
        if(ch) {
            return selectedNote;
        }else {
            return null;
        }
    }


    /**
     * delete
     *
     */

    @Override
    public void deleteItem(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Notes", "user_email" + " = ?", new String[]{email});

    }
    public void deleteLastNoteById(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Notes", "user_id = ? AND noteId = (SELECT MAX(noteId) FROM Notes WHERE user_id = ?)", new String[]{String.valueOf(id), String.valueOf(id)});
        db.close();
    }
    public void deleteLastNoteByEmail(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String deleteQuery = "user_email = ? AND noteId = (SELECT MAX(noteId) FROM Notes WHERE user_email = ?)";
        db.delete("Notes", deleteQuery, new String[]{email, email});
        db.close();
    }
    public void deleteNoteById(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Notes", "noteId = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    @Override
    public void updateUserEmail(String email, String newEmail) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_email", newEmail);
        db.update("Notes", values, "user_email" + " = ?", new String[]{email});
        db.close();

    }


    public void updateNoteTitle(String Title, String newTitle) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("noteTitle", newTitle);
        db.update("Notes", values, "noteTitle" + " = ?", new String[]{Title});
        db.close();
    }
    public void updateNoteDescription(String Description, String newDescription) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("noteDescription", newDescription);
        db.update("Notes", values, "noteDescription" + " = ?", new String[]{Description});
        db.close();
    }

}
