package com.example.a190508003_sevval_imre;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SI_GirisSayfasi extends AppCompatActivity {
    EditText si_email,si_sifre;
    private FirebaseAuth si_mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_giris_);
        si_email = findViewById(R.id.si_giris_email);
        si_sifre = findViewById(R.id.si_giris_sifre);
        si_mAuth = FirebaseAuth.getInstance();
        FirebaseUser si_users = si_mAuth.getCurrentUser();
        if(si_users != null){
            startActivity(new Intent(SI_GirisSayfasi.this, SI_Anasayfa.class));
            finish();
        }
    }
    public void si_kayit_ol(View view) {
        startActivity(new Intent(SI_GirisSayfasi.this, SI_Kayit.class));
    }
    public void si_giris_yap(View view) {
        si_mAuth.signInWithEmailAndPassword(si_email.getText().toString(),si_sifre.getText().toString()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> si_task) {
                        if (si_task.isSuccessful()) {
                            Log.d("SI_GİRİS", "Giriş Yapılacak");
                            FirebaseUser user = si_mAuth.getCurrentUser();
                            Toast.makeText(SI_GirisSayfasi.this, "Giriş başarılı.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SI_GirisSayfasi.this, SI_Anasayfa.class));
                        } else {
                            Log.w("SI_GİRİS", "Giriş Yapılacak", si_task.getException());
                            Toast.makeText(SI_GirisSayfasi.this, "Girilen email veya şifre hatalıdır", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}