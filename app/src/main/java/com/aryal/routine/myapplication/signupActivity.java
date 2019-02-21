package com.aryal.routine.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextUsername = findViewById(R.id.usernametxt);
        editTextPassword = findViewById(R.id.passwordtxt);
        findViewById(R.id.logintxt).setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.signupbtn).setOnClickListener(this);

    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (username.isEmpty()) {
            editTextUsername.setError("Email is required");
            editTextUsername.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUsername.setError("Email pattern error");
            editTextUsername.requestFocus();
            return;

        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                if (task.isCanceled()) {
                    Toast.makeText(getApplicationContext(), "Registration is Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupbtn:
                registerUser();
                break;
            case R.id.logintxt:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
