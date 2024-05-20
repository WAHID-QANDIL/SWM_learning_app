package com.dtu.swm_learningapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.swm_learningapp.databinding.FragmentHomeUserBinding;
import com.dtu.swm_learningapp.model.User;
import com.dtu.swm_learningapp.ui.HomeUserVM;
import com.dtu.swm_learningapp.util.ResetUserInfo;
import com.dtu.swm_learningapp.util.ToastMaker;
import com.dtu.swm_learningapp.util.UserDataFetcher;
import com.dtu.swm_learningapp.util.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class HomeUserFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeUserBinding binding;
    ResetUserInfo resetUserInfo;
    FirebaseAuth firebaseAuth;
    private HomeUserVM homeUserVM;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init view model
        homeUserVM = new ViewModelProvider(this).get(HomeUserVM.class);
        firebaseAuth = FirebaseAuth.getInstance();
        resetUserInfo = new ResetUserInfo(firebaseAuth.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btSave.setOnClickListener(this);
        binding.btResetPass.setOnClickListener(this);
        homeUserVM.requestUserData(requireContext());
        observeUSerData();
    }

    private void observeUSerData() {
        homeUserVM.getUserData().observe(getViewLifecycleOwner(), _user -> {
            user = _user;
            setDataToFields();
        });

    }

    private void setDataToFields() {
        binding.etName.setText(user.getName());
        binding.etEmail.setText(user.getEmail());
        binding.etPhone.setText(user.getPhone());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btSave.getId()) {

            if (isValid(binding.etName.getText().toString(),
                    binding.etEmail.getText().toString(),
                    binding.etPhone.getText().toString(),
                    binding.etOldPass.getText().toString())) {

                resetUserInfo.updateNameInFireStore(getContext(),
                        binding.etName.getText().toString(), user.getName());

                resetUserInfo.updatePhoneInFireStore(getContext(),
                        binding.etPhone.getText().toString(), user.getPhone());

                resetUserInfo.reAuthenticateAndUpdateEmail(requireContext(), Objects.requireNonNull(firebaseAuth.getCurrentUser()),
                        binding.etOldPass.getText().toString(),
                        binding.etEmail.getText().toString());

            }
        }
        if (v.getId() == binding.btResetPass.getId() &&
                isPassValid(binding.etOldPass.getText().toString(),
                        binding.etNewPass.getText().toString())) {
            ToastMaker.toastShower(requireContext(), "here");
            resetUserInfo.reAuthenticateAndChangePassword(requireContext(), Objects.requireNonNull(firebaseAuth.getCurrentUser()),
                    binding.etOldPass.getText().toString(),
                    binding.etNewPass.getText().toString());
        }
    }


    public boolean isValid(String name, String email, String phone, String pass) {
        if (!Validation.nameIsValid(name)) {
            //showing error massage if the name isn`t valid
            binding.etName.setError("name is not valid");
            return false;
        }

        if (!Validation.emailIsValid(email)) {
            //showing error massage if the email isn`t valid
            binding.etEmail.setError("email is not correctly formatted");
            return false;
        }
        if (!Validation.phoneIsValid(phone)) {
            //showing error massage if the mobile number isn`t valid

            binding.etPhone.setError("mobile number is not valid");
            return false;
        }
        if (!Validation.passIsValid(pass)) {
            binding.etOldPass.setError("please enter a valid password");
            return false;
        }

        return true;
    }

    public boolean isPassValid(String oldPass, String newPass) {
        if (!Validation.passIsValid(newPass) && !Validation.passIsValid(oldPass)) {
            binding.etOldPass.setError("please enter a valid password");
            return false;
        }
        return true;
    }
}