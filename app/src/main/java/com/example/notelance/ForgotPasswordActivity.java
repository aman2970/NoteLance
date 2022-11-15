package com.example.notelance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ForgotPasswordActivity extends AppCompatActivity {
    private AppCompatButton forgot_password_button, go_back_button;
    private EditText forgot_password_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_password_button = findViewById(R.id.forgot_password_button);
        go_back_button = findViewById(R.id.go_back_button);
        forgot_password_email = findViewById(R.id.forgot_password_email);

        go_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
}