package com.example.bomodoro;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BreakTimerFragment extends Fragment {

    private TextView textViewTimer;
    private Button buttonStopBreak;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_break_timer, container, false);
        textViewTimer = view.findViewById(R.id.textViewTimer);
        buttonStopBreak = view.findViewById(R.id.buttonStopBreak);

        startTimer();

        buttonStopBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBreak();
            }
        });

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) { // 5 minutes in milliseconds
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                playSound();
                // Optionally, return to the main activity or close fragment
            }
        }.start();
    }

    private void updateTimer(long millisecondsUntilFinished) {
        int minutes = (int) millisecondsUntilFinished / 60000;
        int seconds = (int) (millisecondsUntilFinished % 60000) / 1000;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeLeftFormatted);
    }

    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.finish_sound);
        }
        mediaPlayer.start();
    }

    private void stopBreak() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Return to the main activity by removing the current fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
