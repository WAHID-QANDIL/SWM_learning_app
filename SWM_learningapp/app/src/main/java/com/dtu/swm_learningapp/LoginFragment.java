package com.dtu.swm_learningapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtu.swm_learningapp.databinding.FragmentLoginBinding;
import com.dtu.swm_learningapp.util.Validation;

import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private FragmentLoginBinding binding;
    private static final String TAG="LoginFragment";

    FirebaseAuth fAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the binding object
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setting a on click listener when pressing on the login button
        binding.btLogin.setOnClickListener(this);
        binding.tRegister.setOnClickListener(this);
        binding.navController.setOnClickListener(this);
        if (fAuth.getCurrentUser() != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.tRegister.getId()||v.getId()==binding.navController.getId()){
            //navigate it towards register fragment
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        }
        // extracting text from the editText
        String email = binding.etEmail.getText().toString();
        String pass = binding.etPass.getText().toString();
        if (v.getId() == binding.btLogin.getId() && isValid(email, pass)) {
            fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "logged in successfully", Toast.LENGTH_SHORT).show();
                    //going to home activity
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    Toast.makeText(getContext(), " wrong email or password", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), " error", Toast.LENGTH_SHORT).show();
        }
    }

    //checking for all validation
    public boolean isValid(String email, String pass) {
        if (!Validation.emailIsValid(email)) {
            binding.etEmail.setError("email is not correctly formatted");
            return false;
        }
        if (!Validation.passIsValid(pass)) {
            binding.etPass.setError("password is not valid ");
            return false;
        }
        return true;
    }
}