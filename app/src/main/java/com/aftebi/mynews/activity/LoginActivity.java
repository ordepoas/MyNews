package com.aftebi.mynews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.aftebi.mynews.R;
import com.aftebi.mynews.databinding.ActivityLoginBinding;
import com.aftebi.mynews.service.FirebaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //---- init config
        dbAuth = FirebaseConfig.getAuth();

        binding.loginButton.setOnClickListener(view -> {
            login();
        });

        binding.registerButton.setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }

    public void login(){

        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if((email == "" || email.isEmpty()) || (password =="" || password.isEmpty()) ) {
            Toast.makeText(LoginActivity.this, "Campos não preenchidos", Toast.LENGTH_SHORT).show();
        } else {
            dbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "O utilizador não existe, registe-se primeiro!";
                        Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Credenciais erradas!";
                        Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, exception + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(dbAuth.getCurrentUser() !=null)
            finish();
    }
}