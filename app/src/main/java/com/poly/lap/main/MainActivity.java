package com.poly.lap.main;

import static com.poly.lap.detail.DetailActivity.EXTRA_BOOK_TO_DETAIL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.poly.lap.add.AddBookActivity;
import com.poly.lap.databinding.ActivityMainBinding;
import com.poly.lap.detail.DetailActivity;
import com.poly.lap.models.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BookAdapter adapter;
    private List<Book> mListBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvBook.setLayoutManager(layoutManager);
        mListBook = new ArrayList<>();
        adapter = new BookAdapter(mListBook, new BookAdapter.IOnEventBookListener() {
            @Override
            public void onClickView(Book book) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_BOOK_TO_DETAIL, book.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Book book) {
                db.collection("books").document(book.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.rvBook.setAdapter(adapter);
        binding.imbAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddBookActivity.class));
        });

        db.collection("books").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mListBook = new ArrayList<>();
                for (QueryDocumentSnapshot snapshot : value){
                    mListBook.add(new Book(
                            snapshot.getId(),
                            snapshot.get("name")+"",
                            Double.parseDouble(snapshot.get("price")+""),
                            snapshot.get("actor")+"",
                            snapshot.get("image")+""
                            ));
                }
                adapter.setList(mListBook);
            }
        });

    }
}