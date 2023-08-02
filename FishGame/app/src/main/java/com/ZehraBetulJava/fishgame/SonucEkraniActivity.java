package com.ZehraBetulJava.fishgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SonucEkraniActivity extends AppCompatActivity {
    private TextView textViewYS;
    private TextView textViewBS;
    private AppCompatButton buttonTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });



        textViewYS = findViewById(R.id.textViewYS);
        textViewBS = findViewById(R.id.textViewBS);
        buttonTry = findViewById(R.id.buttonTry);

        int skor = getIntent().getIntExtra("skor",0);
        textViewYS.setText(String.valueOf(skor));

        SharedPreferences sp = getSharedPreferences("Sonuc", Context.MODE_PRIVATE);
        int enYuksekSkor = sp.getInt("enYuksekSkor",0);

        if (skor > enYuksekSkor){

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("enYuksekSkor",skor);
            editor.commit();

            textViewBS.setText(String.valueOf(skor));

        }else{
            textViewBS.setText(String.valueOf(enYuksekSkor));
        }

        buttonTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SonucEkraniActivity.this,MainActivity.class));
                finish();

            }
        });
    }

}