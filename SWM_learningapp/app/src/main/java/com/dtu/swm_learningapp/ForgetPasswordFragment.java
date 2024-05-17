package com.dtu.swm_learningapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View buttomSheet = inflater.inflate(R.layout.fragment_forget_password, container, false);

        buttomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();
            }
        });
        return buttomSheet;
    }
}