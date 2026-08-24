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

public class CreateAccount extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnCreateAc;
    TextView tvSinIn;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        btnCreateAc=findViewById(R.id.btnCreateAc);
        tvSinIn=findViewById(R.id.tvSignIn);

        firebaseAuth=FirebaseAuth.getInstance();

        btnCreateAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name= etName.getText().toString().trim();
                String  email = etEmail.getText().toString().trim();
                String  password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();



                if(name.isEmpty()){
                    Toast.makeText(CreateAccount.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                if(email.isEmpty()){
                    Toast.makeText(CreateAccount.this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()){
                    Toast.makeText(CreateAccount.this, "Please Enter Your Strong Password", Toast.LENGTH_SHORT).show();
                }
                if(password.length()<6){
                    Toast.makeText(CreateAccount.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                if(password.equals(confirmPassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(CreateAccount.this, "Your Account Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            }else{
                                Toast.makeText(CreateAccount.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });              }

             }
        });

        tvSinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });

    }
}