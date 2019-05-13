package com.example.appnotifirev2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView status, namaDevice;
    Button emergencyCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        namaDevice = findViewById(R.id.deviceName);
        namaDevice.setText("Notifire 1");
        emergencyCall = findViewById(R.id.emergencyCallButton);
        emergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hubungiPemadam();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("alat/notifire_1");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("status").getValue().toString();
                switch (value){
                    case "0":
                        status.setText("Aman");
                        emergencyCall.setVisibility(View.INVISIBLE);
                        break;
                    case "1":
                        status.setText("Kebakaran");
                        emergencyCall.setVisibility(View.VISIBLE);
                        break;
                }
                //status.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void hubungiPemadam(){
        String nomor="08781123";
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            String uri = "tel:"+nomor;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));// Initiates the Intent
            startActivity(intent);
        }
        else{
            String uri = "tel:"+nomor;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));// Initiates the Intent
            startActivity(intent);
        }
    }
}
