package com.example.a190508003_sevval_imre;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class SI_YeniBesinEkle extends AppCompatActivity {
    ImageView si_resim;
    String si_secilenCinsiyet;
    StringBuilder si_icecek_ekle;
    Spinner si_ogunler_ekle;
    EditText si_besinAdi_ekle, si_porsiyon_ekle;
    RadioGroup si_cinsiyet;
    RadioButton si_kadin, si_erkek;
    Button si_besinkaydi_olustur;
    Bitmap si_bp;
    FirebaseStorage si_mstorage;
    FirebaseFirestore si_vt;
    String si_URL;
    private static final int SI_CAMERA_REQUEST_CODE = 0606;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_yeni_besin_ekle);
        si_resim = findViewById(R.id.si_besinResmi_yeni);
        si_besinAdi_ekle = findViewById(R.id.si_besinAdi_yeni);
        si_porsiyon_ekle = findViewById(R.id.si_porsiyon_yeni);
        si_ogunler_ekle = findViewById(R.id.si_ogun_yeni);
        si_besinkaydi_olustur = findViewById(R.id.si_btn_kayitet_yeni);
        si_mstorage = FirebaseStorage.getInstance();
        si_vt = FirebaseFirestore.getInstance();
        si_besinkaydi_olustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                si_besinkaydi_olustur_click(view);
            }
        });
        si_resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                si_resimEkle();
            }
        });
        Button si_anasayfa = findViewById(R.id.si_anasayfa_yeni);
        si_anasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SI_YeniBesinEkle.this, SI_Anasayfa.class));
                finish();
            }
        });
    }
    public void si_resimEkle() {
        if (ActivityCompat.checkSelfPermission(SI_YeniBesinEkle.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Intent si_takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(si_takePictureIntent, SI_CAMERA_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(SI_YeniBesinEkle.this,
                    new String[]{Manifest.permission.CAMERA}, SI_CAMERA_REQUEST_CODE);
        }
    }
        @Override
        public void onRequestPermissionsResult(int si_requestCode, @NonNull String[] si_permissions, @NonNull int[] si_grantResults) {
            super.onRequestPermissionsResult(si_requestCode, si_permissions, si_grantResults);
            if (si_requestCode == SI_CAMERA_REQUEST_CODE && si_grantResults.length > 0 && (si_grantResults[0] + si_grantResults[1]) == PackageManager.PERMISSION_GRANTED){
                Intent si_takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(si_takePictureIntent, SI_CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(SI_YeniBesinEkle.this, "İzin verilmedi", Toast.LENGTH_LONG).show();
                Log.d("Hata", "İzin verilmedi");
            }
        }
    @Override
    protected void onActivityResult(int si_requestCode, int si_resultCode, @Nullable Intent si_data){
        super.onActivityResult(si_requestCode, si_resultCode, si_data);
        if (si_requestCode == SI_CAMERA_REQUEST_CODE && si_resultCode == Activity.RESULT_OK) {
            Bundle extras = si_data.getExtras();
            si_bp = (Bitmap) si_data.getExtras().get("data");
            si_resim.setImageBitmap(si_bp);
            ByteArrayOutputStream si_baostream = new ByteArrayOutputStream();
            si_bp.compress(Bitmap.CompressFormat.PNG, 100, si_baostream);
            byte[] si_byteArray = si_baostream.toByteArray();
            File si_file = new File(Environment.getDataDirectory() +"/data/"+getPackageName()+"/"+"foto.jpg");
            try{
                FileOutputStream si_fos = new FileOutputStream(si_file);
                si_fos.write(si_byteArray, 0, si_byteArray.length);
                si_fos.close();
                si_baostream.close();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } else if (si_requestCode == RESULT_CANCELED) {
            Toast.makeText(SI_YeniBesinEkle.this, "Hata!!!", Toast.LENGTH_LONG).show();
        }
    }
    private void si_besinkaydi_olustur_click(View view) {
       si_kadin = (RadioButton)findViewById(R.id.si_kadin);
       si_erkek = (RadioButton)findViewById(R.id.si_erkek);
       si_cinsiyet = (RadioGroup)findViewById(R.id.si_cinsiyet_yeni);
       int si_rbCinsiyet = si_cinsiyet.getCheckedRadioButtonId();
       si_secilenCinsiyet=((RadioButton)findViewById(si_cinsiyet.getCheckedRadioButtonId())).getText().toString();
       CheckBox si_su = findViewById(R.id.si_su);
       CheckBox si_kahve = findViewById(R.id.si_kahve);
       CheckBox si_cay = findViewById(R.id.si_cay);
       CheckBox si_gazliicecek = findViewById(R.id.si_gazliicecek);
       si_icecek_ekle=new StringBuilder();
       if(si_su.isChecked())
           si_icecek_ekle.append("Su ");
       if(si_kahve.isChecked())
           si_icecek_ekle.append(" Kahve ");
       if(si_cay.isChecked())
           si_icecek_ekle.append(" Çay ");
       if(si_gazliicecek.isChecked())
           si_icecek_ekle.append(" Gazlı İçecek ");

        StorageReference si_storageRef = si_mstorage.getReference();
        long si_time = System.nanoTime();
        StorageReference si_mountainImagesRef = si_storageRef.child("SI_Besinler/img"+ String.valueOf(si_time)+".jpg");
        Bitmap si_bm = ((BitmapDrawable) si_resim.getDrawable()).getBitmap();
        ByteArrayOutputStream si_baos = new ByteArrayOutputStream();
        si_bm.compress(Bitmap.CompressFormat.JPEG, 100, si_baos);
        byte[] si_data = si_baos.toByteArray();
        UploadTask si_upload = si_mountainImagesRef.putBytes(si_data);
        si_upload.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(SI_YeniBesinEkle.this, "Foto yüklenirken sorun oluştu", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> si_result = taskSnapshot.getStorage().getDownloadUrl();
                    si_result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri si_uri) {
                            si_URL = si_uri.toString();
                            Toast.makeText(SI_YeniBesinEkle.this,"Foto başarılı şekilde yüklendi", Toast.LENGTH_SHORT).show();
                            Log.d("URL", si_URL);
                            Log.d("URL", si_besinAdi_ekle.getText().toString());
                            Log.d("URL", si_porsiyon_ekle.getText().toString());
                            SI_BesinKaydi(si_besinAdi_ekle.getText().toString(), si_porsiyon_ekle.getText().toString(),si_ogunler_ekle.getSelectedItem().toString(),
                                    si_icecek_ekle.toString(),si_secilenCinsiyet,si_URL);
                        }
                    });
                }
            }
        });
    }
    private void SI_BesinKaydi(String si_besinadi_, String si_porsiyon_, String si_ogun_, String si_icecek_, String si_cinsiyet_,String si_URL_) {
        Map<String, Object> si_besinkaydi = new HashMap<>();
        si_besinkaydi.put("besinadi", si_besinadi_);
        si_besinkaydi.put("porsiyon", si_porsiyon_);
        si_besinkaydi.put("ogun",si_ogun_);
        si_besinkaydi.put("icecek",si_icecek_);
        si_besinkaydi.put("cinsiyet",si_cinsiyet_);
        si_besinkaydi.put("url", si_URL_);
        si_vt.collection("SI_Besinlerr").add(si_besinkaydi).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Yeni Besin Kaydı", "Yeni Besin Kaydı Yapılmıştır. " + documentReference.getId());
                        Toast.makeText(SI_YeniBesinEkle.this,"Kayıt Başarılı.", Toast.LENGTH_SHORT).show();
                        si_resim.setImageResource(R.drawable.camera);
                        si_besinAdi_ekle.setText("");
                        si_porsiyon_ekle.setText("");
                        si_ogunler_ekle.getSelectedItem().toString();
                        si_icecek_ekle.toString();
                        si_secilenCinsiyet.isEmpty();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Yeni Besin Kaydı", "Beklenmeyen bir hata oluştu", e);
                    }
                });
    }
}