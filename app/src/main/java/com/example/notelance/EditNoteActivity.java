package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notelance.databinding.ActivityEditNoteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {
    private ActivityEditNoteBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.editTitleEt.setText(getIntent().getStringExtra("title"));
        binding.editContentEt.setText(getIntent().getStringExtra("description"));
        Log.d("edit_id>>>>", getIntent().getStringExtra("docId"));


        binding.saveButton.setOnClickListener(view -> {
            String newTitle = Objects.requireNonNull(binding.editTitleEt.getText()).toString().trim();
            String newContent = Objects.requireNonNull(binding.editContentEt.getText()).toString().trim();

            if(newTitle.isEmpty()){
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
                return;
            }else{
                DocumentReference documentReference =firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(getIntent().getStringExtra("docId"));
                Map<String,Object> note = new HashMap<>();
                note.put("title",newTitle);
                note.put("description",newContent);
                documentReference.set(note).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Note is updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,NotesActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }
}