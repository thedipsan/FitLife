package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;

    Button btnSignIn;

    TextView tvForgetPassword;
    TextView tvCreateAc;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_sign_in);


        // CONNECT XML VIEWS

        etEmail = findViewById(R.id.etSignInEmail);

        etPassword = findViewById(R.id.etSignInPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        tvForgetPassword =
                findViewById(R.id.tvForgotPassword);

        tvCreateAc =
                findViewById(R.id.tvCreateAc);


        // INITIALIZE FIREBASE

        firebaseAuth = FirebaseAuth.getInstance();


        // SIGN IN BUTTON

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                signInUser();
            }
        });


        // FORGOT PASSWORD

        tvForgetPassword.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(
                                SignIn.this,
                                ForgotPassword.class
                        );

                        startActivity(intent);
                    }
                }
        );


        // CREATE ACCOUNT

        tvCreateAc.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(
                                SignIn.this,
                                CreateAccount.class
                        );

                        startActivity(intent);
                    }
                }
        );
    }


    // SIGN IN METHOD

    private void signInUser() {

        String email =
                etEmail.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();


        // VALIDATE EMAIL

        if (email.isEmpty()) {

            etEmail.setError("Email is required");

            etEmail.requestFocus();

            return;
        }


        // VALIDATE PASSWORD

        if (password.isEmpty()) {

            etPassword.setError("Password is required");

            etPassword.requestFocus();

            return;
        }


        // DISABLE BUTTON

        btnSignIn.setEnabled(false);

        btnSignIn.setText("Signing In...");


        // FIREBASE SIGN IN

        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {


                        // GET CURRENT USER

                        FirebaseUser user =
                                firebaseAuth.getCurrentUser();


                        if (user != null) {

                            Toast.makeText(
                                    SignIn.this,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                            ).show();


                            // OPEN DASHBOARD

                            Intent intent =
                                    new Intent(
                                            SignIn.this,
                                            Dashboard.class
                                    );

                            startActivity(intent);

                            finish();

                        } else {

                            btnSignIn.setEnabled(true);

                            btnSignIn.setText("Sign In");

                            Toast.makeText(
                                    SignIn.this,
                                    "User information not found",
                                    Toast.LENGTH_LONG
                            ).show();
                        }


                    } else {

                        // LOGIN FAILED

                        btnSignIn.setEnabled(true);

                        btnSignIn.setText("Sign In");


                        String errorMessage =
                                "Sign in failed";

                        if (task.getException() != null) {

                            errorMessage =
                                    task.getException()
                                            .getMessage();
                        }


                        Toast.makeText(
                                SignIn.this,
                                errorMessage,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}