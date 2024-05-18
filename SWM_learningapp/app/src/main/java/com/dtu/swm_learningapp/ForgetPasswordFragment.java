package com.dtu.swm_learningapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.swm_learningapp.databinding.FragmentForgetPasswordBinding;
import com.dtu.swm_learningapp.util.ToastMaker;
import com.dtu.swm_learningapp.util.Validation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    static FirebaseAuth fAuth;
    private FragmentForgetPasswordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btSend.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == binding.btSend.getId() && Validation.emailIsValid(Objects.requireNonNull(binding.etEmail.getText()).toString())) {
            String email = binding.etEmail.getText().toString();
            fAuth.sendPasswordResetEmail(email).addOnSuccessListener(unused ->{
                                    dismiss();
                                    Snackbar.make(requireView(),
                                    "An email was send to "+ binding.etEmail.getText().toString(),
                                    Snackbar.LENGTH_LONG).show();
            }).
            addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                            dismiss();
                            Snackbar.make(requireView(),
                            "Can't get email like this "+ binding.etEmail.getText().toString(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}