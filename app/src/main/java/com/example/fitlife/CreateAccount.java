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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccount extends AppCompatActivity {

    // UI elements
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;

    Button btnCreateAc;
    TextView tvSinIn;

    // Firebase
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_create_account);


        // CONNECT XML VIEWS

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnCreateAc = findViewById(R.id.btnCreateAc);
        tvSinIn = findViewById(R.id.tvSignIn);


        // INITIALIZE FIREBASE

        firebaseAuth = FirebaseAuth.getInstance();


        // CREATE ACCOUNT BUTTON

        btnCreateAc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createAccount();
            }
        });


        // SIGN IN BUTTON

        tvSinIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        CreateAccount.this,
                        SignIn.class
                );

                startActivity(intent);
            }
        });
    }


    // CREATE ACCOUNT METHOD

    private void createAccount() {

        // Get values from EditTexts
        String name = etName.getText().toString().trim();

        String email = etEmail.getText().toString().trim();

        String password = etPassword.getText().toString().trim();

        String confirmPassword =
                etConfirmPassword.getText().toString().trim();


        // VALIDATION

        if (name.isEmpty()) {

            etName.setError("Please enter your name");
            etName.requestFocus();

            return;
        }


        if (email.isEmpty()) {

            etEmail.setError("Please enter your email");
            etEmail.requestFocus();

            return;
        }


        if (password.isEmpty()) {

            etPassword.setError("Please enter your password");
            etPassword.requestFocus();

            return;
        }


        if (password.length() < 6) {

            etPassword.setError(
                    "Password must be at least 6 characters"
            );

            etPassword.requestFocus();

            return;
        }


        if (confirmPassword.isEmpty()) {

            etConfirmPassword.setError(
                    "Please confirm your password"
            );

            etConfirmPassword.requestFocus();

            return;
        }


        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError(
                    "Passwords do not match"
            );

            etConfirmPassword.requestFocus();

            return;
        }


        // CREATE FIREBASE ACCOUNT

        btnCreateAc.setEnabled(false);

        btnCreateAc.setText("Creating Account...");


        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {


                                // ACCOUNT CREATED

                                if (task.isSuccessful()) {

                                    FirebaseUser user =
                                            firebaseAuth.getCurrentUser();


                                    if (user != null) {

                                        // SAVE USER NAME

                                        UserProfileChangeRequest
                                                profileUpdates =
                                                new UserProfileChangeRequest
                                                        .Builder()
                                                        .setDisplayName(name)
                                                        .build();


                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {

                                                            @Override
                                                            public void onComplete(
                                                                    @NonNull Task<Void> profileTask) {


                                                                if (profileTask.isSuccessful()) {

                                                                    Toast.makeText(
                                                                            CreateAccount.this,
                                                                            "Account created successfully!",
                                                                            Toast.LENGTH_SHORT
                                                                    ).show();


                                                                    // OPEN DASHBOARD

                                                                    Intent intent =
                                                                            new Intent(
                                                                                    CreateAccount.this,
                                                                                    Dashboard.class
                                                                            );

                                                                    startActivity(intent);

                                                                    finish();


                                                                } else {

                                                                    btnCreateAc.setEnabled(true);

                                                                    btnCreateAc.setText(
                                                                            "Create Account"
                                                                    );


                                                                    Toast.makeText(
                                                                            CreateAccount.this,
                                                                            "Account created, but name could not be saved.",
                                                                            Toast.LENGTH_LONG
                                                                    ).show();
                                                                }
                                                            }
                                                        }
                                                );


                                    } else {

                                        btnCreateAc.setEnabled(true);

                                        btnCreateAc.setText(
                                                "Create Account"
                                        );


                                        Toast.makeText(
                                                CreateAccount.this,
                                                "User information not found.",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }


                                } else {

                                    // REGISTRATION FAILED

                                    btnCreateAc.setEnabled(true);

                                    btnCreateAc.setText(
                                            "Create Account"
                                    );


                                    String errorMessage =
                                            "Signup failed";

                                    if (task.getException() != null) {

                                        errorMessage =
                                                task.getException()
                                                        .getMessage();
                                    }


                                    Toast.makeText(
                                            CreateAccount.this,
                                            errorMessage,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        }
                );
    }
}