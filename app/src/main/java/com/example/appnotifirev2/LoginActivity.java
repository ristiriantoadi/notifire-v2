package com.example.appnotifirev2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView username;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        Button register = findViewById(R.id.goToRegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button login  = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();
            }
        });
    }

    private void loginValidation(){

        //cek apa user sudah isi semua form
        if(username.getText().length() != 0 && password.getText().length() != 0){

            //setup path dan reference
            String path = "user/"+username.getText().toString();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(path);

            //cek apa user ada
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //kalau suernya ada, lanjut ke screen berikutnya
                    if(dataSnapshot.getValue() != null){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //toast kalau username/password salah
                        Toast toast = Toast.makeText(getApplicationContext(), "Salah username atau password", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {//kalau user belum isi semua form
            Toast toast = Toast.makeText(getApplicationContext(),"Isi username dan password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
