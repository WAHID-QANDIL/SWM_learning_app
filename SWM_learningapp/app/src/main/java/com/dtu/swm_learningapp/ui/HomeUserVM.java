package com.dtu.swm_learningapp.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dtu.swm_learningapp.model.User;
import com.dtu.swm_learningapp.util.UserDataFetcher;
import com.google.firebase.auth.FirebaseAuth;


public class HomeUserVM extends ViewModel {
    UserDataFetcher userDataFetcher;
    FirebaseAuth firebaseAuth;

    HomeUserVM() {
        firebaseAuth = FirebaseAuth.getInstance();
        userDataFetcher = new UserDataFetcher(firebaseAuth.getUid());
    }

    public void requestUserData(Context context) {
        userDataFetcher.getUserData(context);
    }

    public LiveData<User> getUserData() {
        return userDataFetcher.userMutableLiveData;
    }

}
