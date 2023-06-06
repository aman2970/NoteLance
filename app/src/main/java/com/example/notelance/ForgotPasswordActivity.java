package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notelance.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.goBackButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

        binding.recoverButton.setOnClickListener(view -> {
            String mail = binding.emailEt.getText().toString().trim();
            if(mail.isEmpty()){
                Snackbar.make(view,"Enter your email first",Snackbar.LENGTH_SHORT).show();
            }else{
                // we have to send password recover email
                firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(view,"Mail sent, you can recover your password",Snackbar.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                        }else{
                            Snackbar.make(view,"Email is wrong or account does not exist",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }
}