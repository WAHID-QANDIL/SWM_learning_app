package com.dtu.swm_learningapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtu.swm_learningapp.databinding.FragmentRegisterFragmentBinding;
import com.dtu.swm_learningapp.util.Validation;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    //creating an object from the xml FragmentRegisterFragmentBinding file to use
    private FragmentRegisterFragmentBinding binding;
    //creating a firebase variable
    FirebaseAuth fAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            //going to home activity if hes signed in     i think well its not working so
            //startActivity(new Intent(getContext(),HomeActivity.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the binding object
        binding = FragmentRegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a on click listener when pressing on the register button
        binding.btRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        //checking for all validation
        String email = binding.etEmail.getText().toString();
        String pass = binding.etPass.getText().toString();
        String name = binding.etName.getText().toString();
        String mobNum = binding.etMobNumber.getText().toString();

        if (v.getId() == binding.btRegister.getId() && isValid(name, email, pass, mobNum)) {

            //creating a user profile
            fAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString().trim(),
                    binding.etPass.getText().toString().trim()).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"successfully add",Toast.LENGTH_SHORT).show();
                            //going to home activity    i think well its not working so
                            //startActivity(new Intent(getContext(),HomeActivity.class));
                        }
                        else Toast.makeText(getContext(),"error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

            });

        }
    }

    public boolean isValid(String name, String email, String pass,String mobNum) {
        if(!Validation.nameIsValid(name)){
            binding.etName.setError("name is not valid");
            return false;
        }

        if(!Validation.emailIsValid(email)){
            binding.etEmail.setError("email is not correctly formatted");
            return false;
        }
        if(!Validation.passIsValid(pass)){
            binding.etPass.setError("password is not valid");
            return false;
        }
        if(!Validation.moblieNumberIsValid(mobNum)){
            binding.etMobNumber.setError("mobile number is not valid");
            return false;
        }

        return true;
    }

}