package com.dtu.swm_learningapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dtu.swm_learningapp.databinding.FragmentRegisterFragmentBinding;
import com.dtu.swm_learningapp.util.ToastMaker;
import com.dtu.swm_learningapp.util.UserProfile;
import com.dtu.swm_learningapp.util.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;



public class RegisterFragment extends Fragment implements View.OnClickListener {

    //creating an object from the xml FragmentRegisterFragmentBinding file to use
    private FragmentRegisterFragmentBinding binding;
    //creating a firebaseAuth object
    FirebaseAuth fAuth;

    //creating a FirebaseFirestore object
    FirebaseFirestore fStore;

    //creating a string called userId to store the auto generated userId by firebase in it
    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialising fAuth,fStore
        fAuth = FirebaseAuth.getInstance();

        fStore=FirebaseFirestore.getInstance();
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
        binding.tLogin.setOnClickListener(this);
        binding.navController.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.tLogin.getId() || v.getId() == binding.navController.getId()) {
            //navigate it towards login fragment
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
        }



        if (v.getId() == binding.btRegister.getId()) {
            // extracting text from the editText fields
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPass.getText().toString();
            String name = binding.etName.getText().toString();
            String mobNum = binding.etMobNumber.getText().toString();
            UserProfile userProfile= new UserProfile();
            if (isValid(name, email, pass, mobNum)) {
                //creating a user profile
                fAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString().trim(),
                        binding.etPass.getText().toString().trim()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        ToastMaker.toastShower(getContext(),"successfully add");

                        //getting the auto generated user id to access user document in fire store
                        userId = fAuth.getCurrentUser().getUid();

                        //sending data to saving data function to be correctly prepared and saved properly
                        userProfile.savingData(getContext(),userProfile.preparingData(name, email, pass, mobNum),userId);

                        //going to login page
                        Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
                    } else

                            ToastMaker.toastShower(getContext(),"error");
                });
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

}