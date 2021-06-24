package com.example.a190508003_sevval_imre;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
public class SI_Anasayfa extends AppCompatActivity {
    FirebaseAuth si_mUser;
    RecyclerView si_analiste;
    ArrayList<SI_Besinler> si_BesinListe;
    FirebaseFirestore si_db;
    SI_BesinlerAdapter si_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        si_mUser = FirebaseAuth.getInstance();
        si_analiste = findViewById(R.id.reyclerView);
        si_BesinListe = new ArrayList<>();
        si_db = FirebaseFirestore.getInstance();
        si_adapter = new SI_BesinlerAdapter(si_BesinListe);
        si_analiste.setHasFixedSize(true);
        si_analiste.setLayoutManager(new LinearLayoutManager(SI_Anasayfa.this));
        si_analiste.setAdapter(si_adapter);
        SI_Getir();
    }
    public void SI_Getir(){
        si_db.collection("SI_BesinKaydi").orderBy("si_besin_adi",Query.Direction.ASCENDING).get().addOnCompleteListener
                (new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> si_task) {
                        if (si_task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : si_task.getResult()) {
                                Log.d("getBesinler", document.getId() + " => " + document.getData());
                                Log.d("getBesinler", document.get("si_besin_adi").toString());
                                si_BesinListe.add(new SI_Besinler(document.get("si_besin_adi").toString(), document.get("si_porsiyon").toString(),
                                        document.get("si_ogun").toString(),
                                        document.get("si_icecek").toString(),
                                        document.get("si_cinsiyet").toString(),
                                        document.get("si_url").toString())); }
                            Log.d("getBesinler", si_BesinListe.size()+"");
                            si_adapter.notifyDataSetChanged(); } else {
                            Log.d("getBesinler", "Bir hata oluştu", si_task.getException()); }
                    }
                }); }
    @Override
    public boolean onCreateOptionsMenu(Menu si_menu) {
        getMenuInflater().inflate(R.menu.si_besin_listesi, si_menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem si_item) {
        int si_id = si_item.getItemId();
        switch (si_id){
            case R.id.si_cikisyap:
                Toast.makeText(getApplicationContext(),"Çıkış yapılacak",Toast.LENGTH_LONG).show();
                si_mUser.signOut();
                startActivity(new Intent(SI_Anasayfa.this, SI_GirisSayfasi.class));
                finish();
                return true;
            case R.id.si_kayitekle:
                Toast.makeText(getApplicationContext(),"Yeni Besin Kaydı Oluşturulacak",Toast.LENGTH_LONG).show();
                startActivity(new Intent(SI_Anasayfa.this, SI_YeniBesinEkle.class));
                return true;
            default:
                return super.onOptionsItemSelected(si_item);
        }
    }
}