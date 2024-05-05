package com.dtu.swm_learningapp;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSplashScreen();
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setLoginOnClicks();

    }

    private void setLoginOnClicks()
    {
        TextView registerTextView = findViewById(R.id.login_register_textview);
        ImageView nav_register_imageview = findViewById(R.id.nav_register_imageview);
        registerTextView.setOnClickListener(this);
        nav_register_imageview.setOnClickListener(this);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.login_register_textview || id == R.id.nav_register_imageview)
        {
            RegisterFragment registerFragment = new RegisterFragment();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_in_left)
                        .add(R.id.login_frame_container, registerFragment)
                        .addToBackStack(null)
                        .commit();
            return;
        } else if (id == R.id.nav_button || id == R.id.already_have_account_textview) {
            fragmentManager.popBackStack();
//            FrameLayout loginFrame = findViewById(R.id.login_frame_container);
//            loginFrame.setVisibility(View.GONE);



//            fragmentManager.beginTransaction()
//                    .replace(R.id.login_frame_container,new LoginFragment())
//                    .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
//                    .addToBackStack(null)
//                    .commit();
        } else if (id == R.id.login_button) {
//            FrameLayout loginFrame = findViewById(R.id.login_frame_container);
//            loginFrame.setVisibility(View.GONE);

            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();

        }
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