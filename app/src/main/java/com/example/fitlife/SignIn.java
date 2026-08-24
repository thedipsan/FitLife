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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    EditText etEmail,  etPassword;
    Button btnSignIn;
    TextView tvForgetPassword, tvCreateAc;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        etEmail=findViewById(R.id.etSignInEmail);
        etPassword=findViewById(R.id.etSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvForgetPassword= findViewById(R.id.tvForgotPassword);
        tvCreateAc = findViewById(R.id.tvCreateAc);

        firebaseAuth=FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                if(email.isEmpty()){
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                }
                if(password.isEmpty()){
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                }



                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent= new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(SignIn.this, "Signin Failed, Please Tryagain Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });


        tvCreateAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}