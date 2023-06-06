package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notelance.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null)
        {
            finish();
            startActivity(new Intent(LoginActivity.this,NotesActivity.class));
        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.loginEmailEt.getText().toString().trim();
                String password = binding.loginPasswordEt.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Snackbar.make(view,"All fields are required",Snackbar.LENGTH_SHORT).show();
                }else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                    //login the user
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkMailVerification();
                            }else{
                                Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                }
            }
        });

        binding.newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkMailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(Objects.requireNonNull(firebaseUser).isEmailVerified()){
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this,NotesActivity.class));
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Verify your mail first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}