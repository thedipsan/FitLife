package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    private TextView tvGreeting;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_dashboard);


        // CONNECT XML

        tvGreeting = findViewById(R.id.tvGreeting);


        // INITIALIZE FIREBASE

        firebaseAuth = FirebaseAuth.getInstance();


        // CHECK CURRENT USER

        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user != null) {

            // User is logged in
            loadUserName(user);

        } else {

            // No user is logged in
            goToSignIn();
        }
    }


    // LOAD USER NAME

    private void loadUserName(FirebaseUser user) {

        /*
         * Reload the Firebase user first.
         *
         * This makes sure we get the latest
         * displayName from Firebase.
         */

        user.reload().addOnCompleteListener(
                new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(
                            @NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            // Get updated user
                            FirebaseUser updatedUser =
                                    firebaseAuth.getCurrentUser();


                            if (updatedUser != null) {

                                String name =
                                        updatedUser.getDisplayName();


                                // CHECK NAME

                                if (name != null &&
                                        !name.trim().isEmpty()) {

                                    tvGreeting.setText(
                                            "Hi, " + name
                                    );

                                } else {

                                    /*
                                     * Display name is missing.
                                     * This means the name was not saved
                                     * during registration.
                                     */

                                    tvGreeting.setText(
                                            "Hi, User"
                                    );

                                    Toast.makeText(
                                            Dashboard.this,
                                            "User name is not saved in Firebase",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                            } else {

                                goToSignIn();
                            }

                        } else {

                            // Firebase reload failed
                            tvGreeting.setText("Hi, User");

                            Toast.makeText(
                                    Dashboard.this,
                                    "Unable to load user information",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                }
        );
    }


    // GO TO SIGN IN

    private void goToSignIn() {

        Intent intent = new Intent(
                Dashboard.this,
                SignIn.class
        );

        startActivity(intent);

        finish();
    }
}