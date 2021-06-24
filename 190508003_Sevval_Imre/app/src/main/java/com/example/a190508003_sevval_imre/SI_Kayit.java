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
public class SI_Kayit extends AppCompatActivity {
    EditText si_email,si_sifre,si_adsoyad,si_boy,si_kilo,si_yas;
    private FirebaseAuth si_mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_kayit_ol);
        si_email = findViewById(R.id.si_kayitol_email);
        si_sifre = findViewById(R.id.si_kayitol_sifre);
        si_adsoyad = findViewById(R.id.si_kayitol_adsoyad);
        si_boy = findViewById(R.id.si_kayitol_boy);
        si_kilo = findViewById(R.id.si_kayitol_kilo);
        si_yas = findViewById(R.id.si_kayitol_yas);
        si_mAuth = FirebaseAuth.getInstance();
    }
    public void si_kayitol(View view) {
        String si_uemail = si_email.getText().toString();
        String si_usifre = si_sifre.getText().toString();
        if(!si_uemail.isEmpty() && !si_usifre.isEmpty()){
            si_mAuth.createUserWithEmailAndPassword(si_uemail, si_usifre).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("SI_KAYIT", "Kayıt");
                                FirebaseUser si_kayit_user = si_mAuth.getCurrentUser();
                                startActivity(new Intent(SI_Kayit.this, SI_Anasayfa.class));

                            } else {
                                Log.w("SI_KAYIT", "Kayıt", task.getException());
                                Toast.makeText(SI_Kayit.this, "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SI_Kayit.this, SI_GirisSayfasi.class));
                            }
                        }
                    });
        }
    }
}