package com.dtu.swm_learningapp;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSplashScreen();
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }
    private void initSplashScreen()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            SplashScreen.installSplashScreen(MainActivity.this);
            getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
                final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                        splashScreenView,
                        View.TRANSLATION_X,
                        (float) -splashScreenView.getWidth() /4,
                        splashScreenView.getWidth()
                );
                slideUp.setInterpolator(new AnticipateInterpolator());
                slideUp.setDuration(500L);

                slideUp.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        splashScreenView.remove();
                    }
                });

                slideUp.start();
            });
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }
}