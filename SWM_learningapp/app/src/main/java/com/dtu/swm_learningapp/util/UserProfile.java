package com.dtu.swm_learningapp.util;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    FirebaseFirestore fStore ;
    private static final String TAG = "UserProfile";

    public UserProfile() {
        fStore = FirebaseFirestore.getInstance();
    }

    public Map<String, Object> getUserProfile(String name, String email, String mobNum){
        //making map container to prepare the data that user entered
        Map<String, Object>user=new HashMap<>();
        user.put("name",name);
        user.put("email",email);
        user.put("mobile number",mobNum);
        return user;
    }
    public void savingData(Context context, Map<String, Object>user,String userId){
        //saving the user info in fire store using user id
        Log.d(TAG, "savingData: ");
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.set(user).addOnSuccessListener(unused -> {

        }).addOnFailureListener(e -> {

        });
    }
}
