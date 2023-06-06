package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notelance.databinding.ActivityCreateNoteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateNoteActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private ActivityCreateNoteBinding  binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        binding.saveButton.setOnClickListener(view -> {
            String title = Objects.requireNonNull(binding.titleEt.getText()).toString().trim();
            String description = Objects.requireNonNull(binding.contentEt.getText()).toString().trim();

            if(title.isEmpty()){
                Snackbar.make(view,"Please enter a title",Snackbar.LENGTH_SHORT).show();
            }else if(description.isEmpty()){
                Snackbar.make(view,"Please enter description",Snackbar.LENGTH_SHORT).show();
            }else{
                binding.progressBar.setVisibility(View.VISIBLE);
                DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                Map<String ,Object> note = new HashMap<>();
                note.put("title",title);
                note.put("description",description);
                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateNoteActivity.this, "Note created successfully", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(CreateNoteActivity.this,NotesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(CreateNoteActivity.this, "Failed to create note", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

    }
}