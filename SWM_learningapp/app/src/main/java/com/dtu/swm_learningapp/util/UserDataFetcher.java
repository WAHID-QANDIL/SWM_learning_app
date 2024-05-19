package com.dtu.swm_learningapp.util;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.dtu.swm_learningapp.model.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDataFetcher {
    private FirebaseFirestore db;
    private DocumentReference userRef;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public UserDataFetcher(String userId) {
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("users").document(userId);
    }

    public void getUserData(Context context) {
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    userMutableLiveData.setValue(document.toObject(User.class));
                } else {
                    ToastMaker.toastShower(context, "User document not found.");
                }
            } else {
                ToastMaker.toastShower(context, "Failed to get user document");
            }
        });
    }


}

