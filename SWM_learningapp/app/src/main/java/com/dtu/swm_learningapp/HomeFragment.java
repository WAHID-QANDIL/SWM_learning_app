package com.dtu.swm_learningapp;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dtu.swm_learningapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private FragmentHomeBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the binding object
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LoginFragment.fAuth.signOut(); // to test logic for now
        initListeners();
   }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initListeners()
    {
        binding.logoutButton.setOnClickListener(this);
        binding.HomeCard.setOnClickListener(this);
        binding.notesCard.setOnClickListener(this);
        binding.settingsCard.setOnClickListener(this);
        binding.pomodoroCard.setOnClickListener(this);
    }
//hosam@gmail.com : email
//123456789 : pass
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if  (id == binding.logoutButton.getId())
        {
            LoginFragment.fAuth.signOut();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
            return;
        }
        if (id == binding.HomeCard.getId())
        {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_user_profile);
            return;
        }

        if (id == binding.pomodoroCard.getId())
        {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_bomodoroFragment);
            return;
        }

    }
}
