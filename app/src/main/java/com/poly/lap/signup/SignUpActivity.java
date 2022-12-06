package com.poly.lap.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.poly.lap.R;
import com.poly.lap.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();
        binding.btnSignUp.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
        String email = binding.edEmail.getText().toString().trim();
        String password = binding.edPassword.getText().toString().trim();
        String rePassword = binding.edVerifyPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            binding.edEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            binding.edPassword.requestFocus();
        }else if (!rePassword.equals(password)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            binding.edVerifyPassword.requestFocus();
        }else{
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Created", Toast.LENGTH_SHORT).show();
                        binding.edEmail.setText("");
                        binding.edPassword.setText("");
                        binding.edVerifyPassword.setText("");
                    }else{
                        Toast.makeText(SignUpActivity.this, "Something error !!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}