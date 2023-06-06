package com.example.notelance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notelance.databinding.ActivityEditNoteBinding;

public class EditNoteActivity extends AppCompatActivity {
    private ActivityEditNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}