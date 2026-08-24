package com.example.fitlife;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText etEmail;
    Button btnForgotPassword;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth= FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etForgotEmail);
        btnForgotPassword=findViewById(R.id.btnForgotPassword);



        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();

                }
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()) {
                           Toast.makeText(ForgotPassword.this, "Please check your mail", Toast.LENGTH_SHORT).show();
                           finish();
                       }
                    }
                });
            }
        });
    }
}