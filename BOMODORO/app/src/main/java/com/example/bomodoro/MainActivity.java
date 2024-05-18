package com.example.bomodoro;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Button buttonStart, buttonReset, buttonBreak;
    private TextView textViewTimer, textViewRoundCount;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private long timeLeftInMilliseconds = 1500000; // 25 minutes
    private boolean timerRunning;
    private int roundCount = 0;
    private final int breakTimeInMilliseconds = 300000; // 5 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewRoundCount = findViewById(R.id.textViewRoundCount);
        buttonStart = findViewById(R.id.buttonStart);
        buttonReset = findViewById(R.id.buttonReset);
        buttonBreak = findViewById(R.id.buttonBreak);

        updateTimer();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        buttonBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreak();
            }
        });
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

        buttonStart.setText("Pause");
        timerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        buttonStart.setText("Start");
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
        buttonStart.setText("Start");
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeLeftText);
    }

    private void startBreak() {
        Log.d("MainActivity", "Starting break timer");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BreakTimerFragment breakTimerFragment = new BreakTimerFragment();
        transaction.replace(R.id.fragment_container, breakTimerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.finish_sound);
        }
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
