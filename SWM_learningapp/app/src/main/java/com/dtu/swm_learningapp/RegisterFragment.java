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
import com.dtu.swm_learningapp.util.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    //creating an object from the xml FragmentRegisterFragmentBinding file to use
    private FragmentRegisterFragmentBinding binding;
    //creating a firebase variable
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            // extracting text from the editText
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPass.getText().toString();
            String name = binding.etName.getText().toString();
            String mobNum = binding.etMobNumber.getText().toString();
            if (isValid(name, email, pass, mobNum)) {
                //creating a user profile
                fAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString().trim(),
                        binding.etPass.getText().toString().trim()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Toast.makeText( getContext(),"successfully add",Toast.LENGTH_SHORT).show();
                        userId = fAuth.getCurrentUser().getUid();
                        //Toast.makeText( getContext(),"here1",Toast.LENGTH_SHORT).show();
                        preparingData(name, email, pass, mobNum);

                        //going to login page if register successfully done
                        Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
                    } else
                            ToastMaker.toastShower(getContext(),"error" + task.getException().getMessage());
                });
            }
        }
    }
    public void preparingData(String name,String email,String pass,String mobNum){
        //making map container to prepare the data that user entered
        Map<String, Object>user=new HashMap<>();
        user.put("name",name);
        user.put("email",email);
        user.put("pass",pass);
        user.put("mobile number",mobNum);

        //saving the user info in fire store
        DocumentReference documentReference = fStore.collection("users").document(userId);
        ToastMaker.toastShower(getContext(),"here3");
        documentReference.set(user).addOnSuccessListener(unused -> {

        }).addOnFailureListener(e -> {

        });


    }
    //checking for all validation
    public boolean isValid( String name, String email, String pass,String mobNum) {
        if( !Validation.nameIsValid(name)){
            binding.etName.setError("name is not valid");
            return false;
        }

        if( !Validation.emailIsValid(email)){
            binding.etEmail.setError("email is not correctly formatted");
            return false;
        }
        if( !Validation.passIsValid(pass)){
            binding.etPass.setError("password is not valid");
            return false;
        }
        if( !Validation.moblieNumberIsValid(mobNum)){
            binding.etMobNumber.setError("mobile number is not valid");
            return false;
        }

        return true;
    }

}