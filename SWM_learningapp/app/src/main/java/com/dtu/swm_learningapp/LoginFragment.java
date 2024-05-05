package com.dtu.swm_learningapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dtu.swm_learningapp.databinding.FragmentLoginBinding;
import com.dtu.swm_learningapp.databinding.FragmentRegisterFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding ;

    FirebaseAuth fAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            //going to home activity if hes signed in     i think well its not working so
           // startActivity(new Intent(getContext(),HomeActivity.class));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the binding object
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

}