package com.dtu.swm_learningapp.util;

import android.util.Patterns;
public class Validation {
   public static boolean nameIsValid(String name) {
       return !name.trim().isEmpty();
   }

    public static boolean emailIsValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

   public static boolean passIsValid(String pass) {
       return pass.trim().length() > 6;
   }

    public static boolean phoneIsValid(String mobNumber) {
        return !mobNumber.isEmpty() && android.util.Patterns.PHONE.matcher(mobNumber).matches();
    }

}
