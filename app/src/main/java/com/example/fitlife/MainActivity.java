package com.example.fitlife;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.splash_image);
        textView =  findViewById(R.id.splash_text);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        imageView.startAnimation(animation);
        textView.startAnimation(animation);



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        thread.start();
    }
}