package com.poly.lap.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.poly.lap.databinding.ActivityAddBookBinding;
import com.poly.lap.models.Book;

public class AddBookActivity extends AppCompatActivity {
    private ActivityAddBookBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(view -> {
            handleAdd();
        });

    }

    private void handleAdd() {
        String name = binding.edName.getText().toString().trim();
        String price = binding.edPrice.getText().toString().trim();
        String actor = binding.edActor.getText().toString().trim();
        String url = binding.edImage.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter book name", Toast.LENGTH_SHORT).show();
            binding.edName.requestFocus();
        }else if (TextUtils.isEmpty(binding.edPrice.getText().toString().trim())){
            Toast.makeText(this, "Please enter book price", Toast.LENGTH_SHORT).show();
            binding.edPrice.requestFocus();
        }else if(Double.parseDouble(price) < 0){
            Toast.makeText(this, "Price must be greater than 0", Toast.LENGTH_SHORT).show();
            binding.edPrice.requestFocus();
        }else if (TextUtils.isEmpty(actor)){
            Toast.makeText(this, "Please enter actor", Toast.LENGTH_SHORT).show();
            binding.edActor.requestFocus();
        }else if (TextUtils.isEmpty(url)){
            Toast.makeText(this, "Please enter url image", Toast.LENGTH_SHORT).show();
            binding.edImage.requestFocus();
        }else{
            Book book = new Book(name, Double.parseDouble(price), actor, url);
            db.collection("books")
                    .add(book)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddBookActivity.this, "Create book successfully", Toast.LENGTH_SHORT).show();
                            binding.edImage.setText("");
                            binding.edName.setText("");
                            binding.edActor.setText("");
                            binding.edPrice.setText("");
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBookActivity.this, "Create failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}