package com.aftebi.mynews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aftebi.mynews.databinding.ActivityRegistrationBinding;
import com.aftebi.mynews.service.FirebaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Login");


        //---- init config
        dbAuth = FirebaseConfig.getAuth();

        binding.registerButton.setOnClickListener(View ->{
            register();
        });
    }

    public void register(){

        String name = binding.nameEditText.getText().toString();
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        boolean success = !name.isEmpty() && !password.isEmpty() && !email.isEmpty();

        if(success) {
            //---- user registration in firebase auth
            FirebaseConfig.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            //---- add display name to firebase auth
                            FirebaseUser user = dbAuth.getCurrentUser();
                            UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(update)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()) {
                                               Log.d("INFO", "User profile updated.");
                                           }
                                       }
                                   });
                            finish();
                        } else {
                            String exception = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                exception = "O utilizador já existe";
                                Toast.makeText(this, exception, Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                exception = "Palavra-passe fraca, tente novamente";
                                Toast.makeText(this, exception, Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                exception = "Email inválido";
                                Toast.makeText(this, exception, Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthException e) {
                                Toast.makeText(this, exception + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(this, exception + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Preencha todos os campos",Toast.LENGTH_LONG).show();
        }


    }

}