package com.example.notelance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.notelance.Adapters.NotesAdapter;
import com.example.notelance.Models.FirebaseModel;
import com.example.notelance.databinding.ActivityNotesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding binding;
    private FirebaseAuth firebaseAuth;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    private List<FirebaseModel> firebaseModelList;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseModelList = new ArrayList<>();

        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title",Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                firebaseModelList.clear();
                for (QueryDocumentSnapshot document : task.getResult()){
                    String title = document.getString("title");
                    String description = document.getString("description");
                    FirebaseModel firebaseModel = new FirebaseModel(title,description);
                    firebaseModelList.add(firebaseModel);
                }

                binding.notesRv.setHasFixedSize(true);
                binding.notesRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                notesAdapter = new NotesAdapter(this,firebaseModelList);
                binding.notesRv.setAdapter(notesAdapter);

            }else{

                Log.d("Firebase>>>>", "Error getting documents: ", task.getException());
            }

        });

        binding.createButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,CreateNoteActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}