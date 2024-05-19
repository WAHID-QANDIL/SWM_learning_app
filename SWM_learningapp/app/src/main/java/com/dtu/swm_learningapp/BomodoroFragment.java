package com.dtu.swm_learningapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dtu.swm_learningapp.databinding.FragmentBomodoroBinding;
public class BomodoroFragment extends Fragment implements View.OnClickListener {
    private FragmentBomodoroBinding binding;
    private TextView textViewTimer, textViewRoundCount;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private long timeLeftInMilliseconds = 1500000; // 25 minutes
    private boolean timerRunning;
    private int roundCount = 0;
    private final int breakTimeInMilliseconds = 300000; // 5 minutes

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentBomodoroBinding.inflate(inflater, container, false);
        textViewTimer = binding.textViewTimer;
        textViewRoundCount = binding.textViewRoundCount;
        updateTimer();
        initListeners();
        return  binding.getRoot();

//        textViewTimer = binding..findViewById(R.id.textViewTimer);
//        textViewRoundCount = view.findViewById(R.id.textViewRoundCount);
//        buttonStart = view.findViewById(R.id.buttonStart);
//        buttonReset = view.findViewById(R.id.buttonReset);
//        buttonBreak = view.findViewById(R.id.buttonBreak);
//        buttonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (timerRunning) {
//                    pauseTimer();
//                } else {
//                    startTimer();
//                }
//            }
//        });

//        buttonReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetTimer();
//            }
//        });

//        buttonBreak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startBreak();
//            }
//        });

//        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                roundCount++;
                textViewRoundCount.setText("Round: " + roundCount);
                playSound();
                startBreak();
            }
        }.start();

        binding.buttonStart.setText("Pause");
        timerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        binding.buttonStart.setText("Start");
        timerRunning = false;
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerRunning = false;
        timeLeftInMilliseconds = 1500000; // Reset to 25 minutes
        roundCount = 0;
        textViewRoundCount.setText("Round: 0");
        updateTimer();
        binding.buttonStart.setText("Start");
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeLeftText);
    }

    private void startBreak() {
        Log.d("TimerFragment", "Starting break timer");
        if (countDownTimer != null)
            pauseTimer();

        Navigation.findNavController(requireView()).navigate(R.id.action_bomodoroFragment_to_breakFragment);
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        BreakFragment breakTimerFragment = new BreakFragment();
//        transaction.replace(R.id.fragment_container, breakTimerFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.finish_sound);
        }
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void initListeners()
    {
        binding.buttonStart.setOnClickListener(this);
        binding.buttonReset.setOnClickListener(this);
        binding.buttonBreak.setOnClickListener(this);
        binding.bomodoroBackbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.buttonStart.getId())
        {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
            return;
        }
        if (id == binding.buttonBreak.getId())
        {
            startBreak();
            updateTimer();
            return;
        }
        if (id == binding.buttonReset.getId())
        {
            resetTimer();
            return;
        }
        if (id == binding.bomodoroBackbutton.getId())
        {
            requireActivity().getSupportFragmentManager().popBackStack();
            return;
        }

        return;

    }
}
