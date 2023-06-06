package com.example.notelance.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notelance.EditNoteActivity;
import com.example.notelance.Models.FirebaseModel;
import com.example.notelance.NoteDetailsActivity;
import com.example.notelance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Activity context;
    private List<FirebaseModel> firebaseModelList;

    public NotesAdapter(Activity context, List<FirebaseModel> firebaseModelList) {
        this.context = context;
        this.firebaseModelList = firebaseModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        int colorcode = getRandomColor();
//        holder.noteCv.setBackgroundColor(holder.itemView.getResources().getColor(colorcode,null));

        String docId = firebaseModelList.get(position).getId();

        holder.noteCv.setOnClickListener(view -> {
            //we have to open the note detail activity
            Log.d("docid>>>>",docId);
            Intent intent = new Intent(view.getContext(), NoteDetailsActivity.class);
            intent.putExtra("title",firebaseModelList.get(position).getTitle());
            intent.putExtra("description",firebaseModelList.get(position).getDescription());
            intent.putExtra("docId",docId);
            view.getContext().startActivity(intent);
        });

        holder.menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.setGravity(Gravity.END);
            popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(menuItem -> {
                Intent  intent = new Intent(view.getContext(), EditNoteActivity.class);
                intent.putExtra("title",firebaseModelList.get(position).getTitle());
                intent.putExtra("description",firebaseModelList.get(position).getDescription());
                intent.putExtra("docId",docId);
                view.getContext().startActivity(intent);
                return false;
            });

            popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(menuItem -> {
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("notes").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).collection("myNotes").document(docId);
                documentReference.delete().addOnSuccessListener(unused -> Toast.makeText(context, "This note is deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show());
                return false;
            });
            popupMenu.show();
        });

        holder.titleTv.setText(firebaseModelList.get(position).getTitle());
        holder.contentTv.setText(firebaseModelList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return firebaseModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView contentTv;
        public CardView noteCv;
        public AppCompatImageView menuButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleTv);
            contentTv = itemView.findViewById(R.id.contentTv);
            noteCv = itemView.findViewById(R.id.noteCv);
            menuButton = itemView.findViewById(R.id.menuButton);
        }
    }

    private int getRandomColor() {
        List<Integer> colorcode = new ArrayList<>();
        colorcode.add(R.color.purple_200);
        colorcode.add(R.color.purple_500);
        colorcode.add(R.color.purple_700);
        colorcode.add(R.color.teal_200);
        colorcode.add(R.color.teal_700);
        colorcode.add(R.color.black);

        Random random = new Random();
        int number = random.nextInt(colorcode.size());
        return colorcode.get((number));
    }
}
