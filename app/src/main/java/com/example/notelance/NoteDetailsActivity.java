package com.example.notelance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.notelance.databinding.ActivityNoteDetailsBinding;

public class NoteDetailsActivity extends AppCompatActivity {
    private ActivityNoteDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.titleTv.setText(getIntent().getStringExtra("title"));
        binding.contentTv.setText(getIntent().getStringExtra("description"));

        binding.editButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,EditNoteActivity.class);
            intent.putExtra("title",getIntent().getStringExtra("title"));
            intent.putExtra("description",getIntent().getStringExtra("description"));
            intent.putExtra("docId",getIntent().getStringExtra("docId"));
            startActivity(intent);
        });

    }
}