package com.poly.lap.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.poly.lap.R;
import com.poly.lap.databinding.ActivityDetailBinding;
import com.poly.lap.models.Book;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    public static String EXTRA_BOOK_TO_DETAIL = "extra_detail";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id = getIntent().getStringExtra(EXTRA_BOOK_TO_DETAIL);
        binding.btnAdd.setOnClickListener(view -> {
            handleUpdate(id);
        });
        db.collection("books").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot value) {
                urlImage = value.get("image")+"";
                Glide.with(binding.imgBook.getContext()).load(value.get("image")).into(binding.imgBook);
                binding.edPrice.setText(value.get("price")+"");
                binding.edName.setText(value.get("name")+"");
                binding.edActor.setText(value.get("actor")+"");
            }
        });
    }

    private void handleUpdate(String id) {
        String name = binding.edName.getText().toString().trim();
        String actor = binding.edActor.getText().toString().trim();
        String price = binding.edPrice.getText().toString().trim();
        db.collection("books").document(id).set(new Book(id, name, Double.parseDouble(price), actor, urlImage)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DetailActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}