package com.dtu.swm_learningapp.util;

import android.content.Context;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResetUserInfo {
    static FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    DocumentReference userRef;

    public ResetUserInfo(String userId) {
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("users").document(userId);
    }

    public void updateNameInFireStore(Context context, String newName) {
        if (Validation.nameIsValid(newName)) {
            userRef.update("name", newName)
                    .addOnSuccessListener(unused -> {
                        ToastMaker.toastShower(context, "Name updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        ToastMaker.toastShower(context, "Failed to update name.");
                    });
        } else {
            ToastMaker.toastShower(context, "Invalid name format.");
        }
    }

    public void updatePhoneInFireStore(Context context, String newPhone) {
        if (Validation.phoneIsValid(newPhone)) {
            userRef.update("mobile number", newPhone)
                    .addOnSuccessListener(unused -> {
                        ToastMaker.toastShower(context, "Phone number updated successfully.");
                    })
                    .addOnFailureListener(e -> {
                        ToastMaker.toastShower(context, "Failed to update phone number.");
                    });
        } else {
            ToastMaker.toastShower(context, "Invalid phone number format.");
        }
    }

    public void reAuthenticateAndChangePassword(Context context, FirebaseUser user, String oldPassword, String newPassword) {
        String email = user.getEmail();
        if (email == null) {
            ToastMaker.toastShower(context, "User email not found.");
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        ToastMaker.toastShower(context, "Password updated successfully.");
                                    } else {
                                        ToastMaker.toastShower(context, "Failed to update password.");
                                    }
                                });
                    } else {
                        ToastMaker.toastShower(context, "Reauthentication failed.");
                    }
                });
    }
}