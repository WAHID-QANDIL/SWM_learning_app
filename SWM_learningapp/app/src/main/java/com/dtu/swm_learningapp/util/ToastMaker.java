package com.dtu.swm_learningapp.util;

import android.content.Context;
import android.widget.Toast;

public class ToastMaker {
    public static void toastShower(Context context,String massage){
        Toast.makeText(context,massage,Toast.LENGTH_SHORT).show();



    }
}
