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
    private final DocumentReference userRef;

    public ResetUserInfo(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userRef = db.collection("users").document(userId);
    }

    public void updateNameInFireStore(Context context, String newName ,String oldName ) {
        if (Validation.nameIsValid(newName)) {
            if(newName.equals(oldName)) return;
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

    public void updatePhoneInFireStore(Context context, String newPhone,String oldPhone) {
        if (Validation.phoneIsValid(newPhone)) {
            if(newPhone.equals(oldPhone))return;
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
    public void reAuthenticateAndUpdateEmail(Context context, FirebaseUser user, String oldPassword, String newEmail) {
        String email = user.getEmail();
        if (email == null) {
            ToastMaker.toastShower(context, "User email not found.");
            return;
        }
        if (email.equals(newEmail)) {
            return;
        }
        //update email in fireBase
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.verifyBeforeUpdateEmail(newEmail)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        // Update email in FireStore
                                        userRef.update("email", newEmail)
                                                .addOnSuccessListener(unused -> {
                                                    ToastMaker.toastShower(context, "Email updated successfully.");
                                                })
                                                .addOnFailureListener(e -> {
                                                    ToastMaker.toastShower(context, "Failed to update email in Firestore.");
                                                });
                                    } else {
                                        ToastMaker.toastShower(context, "Failed to update email.");
                                    }
                                });
                    } else {
                        ToastMaker.toastShower(context, "Reauthentication failed.");
                    }
                });
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