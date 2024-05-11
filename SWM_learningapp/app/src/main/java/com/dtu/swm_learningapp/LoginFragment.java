package com.dtu.swm_learningapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dtu.swm_learningapp.databinding.FragmentLoginBinding;
import com.dtu.swm_learningapp.util.ToastMaker;
import com.dtu.swm_learningapp.util.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment implements View.OnClickListener, OnCompleteListener<AuthResult> {
    private FragmentLoginBinding binding;

    static FirebaseAuth fAuth;

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
        binding.navImageviewButton.setOnClickListener(this);
        //if user is already signed in go to home fragment
        if (fAuth.getCurrentUser() != null) {
            //navigate to home fragment
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.tRegister.getId()||v.getId()==binding.navImageviewButton.getId()){
            //navigate it towards register fragment
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        }
        else if (v.getId() == binding.btLogin.getId()) {
            //get email and password to be used
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPass.getText().toString();
            if (isValid(email, pass))
            {
                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this);

            }else
            {
                Snackbar.make(requireView(),R.string.login_wrong_message,Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.colorAccent, requireContext().getTheme())).show();
            }

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

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        String email = binding.etEmail.getText().toString();
        String pass = binding.etPass.getText().toString();
        if (task.isSuccessful() ) {
            Snackbar.make(requireView(),R.string.login_right_message,Snackbar.LENGTH_SHORT).show();
            //going to home activity
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
        } else {
//            ToastMaker.toastShower(getContext()," wrong email or password");
            Snackbar.make(requireView(),R.string.login_wrong_message,Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.colorAccent, requireContext().getTheme())).show();

        }
    }
}