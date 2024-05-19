package com.example.sqlliteproject.models.SqlLite;

import java.util.ArrayList;

public interface DatabaseOperations<T>{
   String addItem(T template);
   void deleteItem(String email);
    ArrayList<T> getAllItems();
    ArrayList<T> getDataByEmail(String email);
    ArrayList<T> getDataByID(long ID);
    void updateUserEmail(String email, String newEmail);

}
