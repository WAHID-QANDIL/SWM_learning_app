package Database_connectivity.Services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlliteproject.models.SqlLite.DatabaseConnection;
import com.example.sqlliteproject.models.SqlLite.DatabaseOperations;
import com.example.sqlliteproject.models.Tables.Users;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database_connectivity.Services.NotesService;

public class UserService implements DatabaseOperations<Users> {

    /**
     * Don't forget  to tasting another database
     */


    private final DatabaseConnection dbHelper;
    Context con;
    public UserService(Context context) {
        dbHelper = new DatabaseConnection(context);
        this.con=context;
    }

    @Override
    public String addItem(Users user) {

        //to cheek if any error in email or password
        String cheek= goodEmailAndPassword(user.getUserEmail(),user.getUserPassword());

        if(cheek.equals("good")) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("userFname", user.getUserFname());
            values.put("userLname", user.getUserLname());
            values.put("userAge", user.getUserAge());
            values.put("userGender", user.getUserGender());
            values.put("userEmail",user.getUserEmail());
            values.put("userPassword", user.getUserPassword());
            db.insert("users", null, values);

            db.close();
        }

        return cheek;
    }

    @Override
    public void deleteItem(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("users", "userEmail" + " = ?", new String[]{email});
        com.example.sqlliteproject.models.Services.ToDoService todo =new com.example.sqlliteproject.models.Services.ToDoService(con);
        todo.deleteItem(email);
        NotesService note =new NotesService(con);
        note.deleteItem(email);
        db.close();

    }

    @Override
    public ArrayList<Users> getAllItems(){

        ArrayList<Users> arrayList = new ArrayList<>();
        SQLiteDatabase s1 = dbHelper.getReadableDatabase();
        Cursor cursor1 = s1.rawQuery("select * from users",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()){
            arrayList.add(new Users(
                    cursor1.getLong(0),
                    cursor1.getString(1),
                    cursor1.getString(2),
                    cursor1.getInt(3),
                    cursor1.getString(4),
                    cursor1.getString(5),
                    cursor1.getString(6)
            ));


            cursor1.moveToNext();
        }
        s1.close();
        return arrayList;
    }

    @Override
    public ArrayList<Users> getDataByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Users u = null;

        try (Cursor cursor = db.rawQuery("SELECT * FROM users WHERE userEmail = ?", new String[]{email})) {
            if (cursor != null && cursor.moveToFirst()) {
                u = new Users(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
            }
        }
         ArrayList<Users> users = new ArrayList<>();
        users.add(u);
        return  users;


//        ArrayList<Users> AllUsers = this.getAllItems();
//        ArrayList<Users> selectedUser=new ArrayList<Users>();
//        boolean ch=false;
//        for(Users user :AllUsers){
//            if(user.getUserEmail().equals(email)){
//                selectedUser.add(user);
//                ch=true;
//            }
//        }
//        if(ch) {
//            return selectedUser;
//        }else {
//            return null;
//        }
//

    }

    @Override
    public ArrayList<Users> getDataByID(long ID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Users u = null;
        String id = String.valueOf(ID);

        try (Cursor cursor = db.rawQuery("SELECT * FROM users WHERE userEmail = ?", new String[]{id})) {
            if (cursor != null && cursor.moveToFirst()) {
                u = new Users(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
            }
        }
        ArrayList<Users> users = new ArrayList<>();
        users.add(u);
        return  users;
    }


    public Users findUser(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Users u = null;

        try (Cursor cursor = db.rawQuery("SELECT * FROM users WHERE userEmail = ?", new String[]{email})) {
            if (cursor != null && cursor.moveToFirst()) {
                u = new Users(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
            }
        }

        return u;

/**
 * another bad way
 */

//        ArrayList<Users> users = getAllItems();
//            for(Users u :users) {
//                if (u.getUserEmail().equals(email)) {
//                    return u;
//                }
//            }


    }



    public String goodEmailAndPassword(String email,String password){
        ArrayList<Users> users = getAllItems();
        for(Users u : users){
            if(u.getUserEmail().equals(email)){
                return " Email Is Already Exist ";
            }
//            if(u.getUserPassword().equals(password)){
//            return " Password Is Already Exist ";
//              }
//            if(!validateEmail(u.getUserEmail())){
//                return " Not Valid Email ";
//            }
//           if(u.getUserPassword().length() < 6){
//               return " Week Password ";
//           }

        }
        return "good";
    }

    public static boolean validateEmail(String email) {
          final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
          final Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
           return matcher.matches();
    }


    /**
     *To UPDATE Users table
     */

    // Assuming you want to update the user's first name
    public int updateUserFname(String email, String newFname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userFname", newFname);
        int result = db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
        return result;
    }
    // Assuming you want to update the user's Last name
    public int updateUserLname(String email, String newLname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userLname", newLname);
        int result = db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
        return result;
    }
    // Assuming you want to update the user's email
    @Override
    public void updateUserEmail(String email, String newEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userEmail", newEmail);
        db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
        //to update the email in each table
        /**
         * to accesses the ToDo functions
         */
        com.example.sqlliteproject.models.Services.ToDoService todo =new com.example.sqlliteproject.models.Services.ToDoService(con);
        todo.updateUserEmail(email,newEmail);

        NotesService note =new NotesService(con);
        note.updateUserEmail(email,newEmail);

    }


    // Assuming you want to update the user's Password
    public void updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userPassword", newPassword);
         db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
    }
    // Assuming you want to update the user's Age
    public void updateUserAge(String email, String newAge) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userAge", newAge);
        db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
    }
    // Assuming you want to update the user's Gender
    public void updateUserGender(String email, String newGender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userGender", newGender);
        db.update("users", values, "userEmail" + " = ?", new String[]{email});
        db.close();
    }

   //Alter Users table
    public void addColumnInUsers(String newColumnName, String newColumnType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("ALTER TABLE users ADD COLUMN " + newColumnName + " " + newColumnType + ';');
        db.close();
    }




}

