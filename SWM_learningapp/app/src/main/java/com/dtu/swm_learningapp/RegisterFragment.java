package com.dtu.swm_learningapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dtu.swm_learningapp.databinding.FragmentRegisterFragmentBinding;
import com.dtu.swm_learningapp.util.UserProfile;
import com.dtu.swm_learningapp.util.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;



public class RegisterFragment extends Fragment implements View.OnClickListener, OnCompleteListener<AuthResult> {

    //creating an object from the xml FragmentRegisterFragmentBinding file to use
    private FragmentRegisterFragmentBinding binding;
    //creating a firebaseAuth object
    private FirebaseAuth fAuth;
    private static final String TAG = "RegisterFragment";

    //creating a FirebaseFirestore object
    private FirebaseFirestore fStore;

    private String email;
    private String pass ;
    private String name ;
    private String mobNum ;
    private UserProfile userProfile ;
    private String userId;
    Snackbar snackbar;

    //creating a string called userId to store the auto generated userId by firebase in it
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialising fAuth,fStore
        fAuth = FirebaseAuth.getInstance();

        fStore=FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        binding.tLogin.setOnClickListener(this);
        binding.navRegisterImageviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.tLogin.getId() || v.getId() == binding.navRegisterImageviewButton.getId()) {
            //navigate it towards login fragment
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
            return;
        }
        if (v.getId() == binding.btRegister.getId()) {
            // extracting text from the editText fields
            email = binding.etEmail.getText().toString();
            pass = binding.etPass.getText().toString();
            name = binding.etName.getText().toString();
            mobNum = binding.etMobNumber.getText().toString();
            if (isValid(name, email, pass, mobNum)) {
                fAuth.createUserWithEmailAndPassword(email.trim(), pass.trim()).addOnCompleteListener(this);
                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment);
                Snackbar.make(requireView(),getResources().getText(R.string.add_new_user_message),
                        Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.colorAccent,
                        requireContext().getTheme())).show();
                fAuth.signOut();
            }
            else
            {
                Snackbar.make(requireView(),getResources().getText(R.string.add_new_user_error_message),Snackbar.LENGTH_SHORT).
                        setTextColor(getResources().
                                getColor(R.color.colorAccent, requireContext().
                                        getTheme())).show();
            }
        }
    }

    //checking for all validation
    public boolean isValid( String name, String email, String pass,String mobNum) {
        if( !Validation.nameIsValid(name)){
            //showing error massage if the name isn`t valid
            binding.etName.setError("name is not valid");
            return false;
        }

        if( !Validation.emailIsValid(email)){
            //showing error massage if the email isn`t valid

            binding.etEmail.setError("email is not correctly formatted");
            return false;
        }
        if( !Validation.passIsValid(pass)){
            //showing error massage if the password isn`t valid

            binding.etPass.setError("password is not valid");
            return false;
        }
        if( !Validation.moblieNumberIsValid(mobNum)){
            //showing error massage if the mobile number isn`t valid

            binding.etMobNumber.setError("mobile number is not valid");
            return false;
        }

        return true;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {

            email = binding.etEmail.getText().toString();
            name = binding.etName.getText().toString();
            mobNum = binding.etMobNumber.getText().toString();
            //creating a user profile
            userProfile= new UserProfile();



            //getting the auto incremented user id to access user document in fire store
            assert fAuth.getCurrentUser() != null; //To avoid to get null when we try to get active user
            userId = fAuth.getCurrentUser().getUid();
            //sending data to saving data function to be correctly prepared and saved properly
            userProfile.savingData(getContext(),userProfile.getUserProfile(name, email, mobNum),userId);
            //navigate to login fragment
            return;
            }
        else
        {
            Log.d(TAG, "onComplete: else statement");
            Snackbar.make(requireView(),getResources().getText(R.string.add_new_user_error_message),Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.colorAccent, requireContext().getTheme())).show();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}