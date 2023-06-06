package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notelance.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
   private ActivitySignupBinding binding;

   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.signupButton.setOnClickListener(view -> {
            String name = binding.nameEt.getText().toString().trim();
            String email = binding.emailEt.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Snackbar.make(view,"All fields are required",Snackbar.LENGTH_SHORT).show();
            }else if(password.length() < 7) {
                Snackbar.make(view,"Password should greater than  7 digits",Snackbar.LENGTH_SHORT).show();
            }else{
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Snackbar.make(view,"Registered successfully",Snackbar.LENGTH_SHORT).show();
                            sendEmailVerification();
                        }else{
                            Snackbar.make(view,"Failed to register",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    //send email verification
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    View view = getWindow().getDecorView().getRootView();
                    Snackbar.make(view,"Verification email sent",Snackbar.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                    finish();
                }
            });
        }else{
            View view = getWindow().getDecorView().getRootView();
            Snackbar.make(view,"Failed to send verification email",Snackbar.LENGTH_SHORT).show();
        }
    }
}