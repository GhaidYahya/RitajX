package com.example.ritajx;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        TextView ritajxText = findViewById(R.id.RitajX);

        // Set initial visibility to INVISIBLE
        ritajxText.setVisibility(View.INVISIBLE);

        LottieTask<LottieComposition> lottieTask = LottieCompositionFactory.fromUrl(this, "https://lottie.host/b02dc796-5241-4f63-a83b-55d3a92af47e/wJkyrafEeM.json");
        lottieTask.addListener(new LottieListener<LottieComposition>() {
            @Override
            public void onResult(LottieComposition composition) {
                animationView.setComposition(composition);

                // Set the repeat count to 0 (play once)
                animationView.setRepeatCount(0);

                // Set the speed to control the duration (adjust as needed)
                animationView.setSpeed(1.0f); // 1.0f is the normal speed

                // Set a listener to be notified when the animation starts
                animationView.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // Show the text when the animation starts
                        ritajxText.setVisibility(View.VISIBLE);

                        // You can add animation to make the text appear (optional)
                        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                        ritajxText.startAnimation(fadeIn);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Hide the text when the animation ends
                        ritajxText.setVisibility(View.INVISIBLE);

                        // Start the MainActivity
                        Intent intent = new Intent(splash_screen.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Optional: close the splash screen activity if you don't want to come back to it
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // Your code for animation cancel
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // Your code for animation repeat
                    }
                });

                animationView.playAnimation();
            }
        });
    }
}
