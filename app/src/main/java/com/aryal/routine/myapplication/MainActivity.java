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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.usernameL);
        editTextPassword = findViewById(R.id.passwordL);
        findViewById(R.id.signuptxt).setOnClickListener(this);
        findViewById(R.id.loginbtn).setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);


    }

    private void userLogin() {

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
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signuptxt:
                startActivity(new Intent(this, signupActivity.class));
                break;

            case R.id.loginbtn:
                userLogin();
                break;


        }

    }
}
