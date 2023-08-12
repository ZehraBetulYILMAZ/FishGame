package com.ZehraBetulJava.fishgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SonucEkraniActivity extends AppCompatActivity {
    private TextView textViewYS;
    private TextView textViewBS;
    private AppCompatButton buttonTry;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

       gecisReklamYukle();

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
                finish();
                startActivity(new Intent(SonucEkraniActivity.this, MainActivity.class));
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SonucEkraniActivity.this);
                    gecisReklamYukle();
                }

            }
        });
    }
    public void gecisReklamYukle() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(SonucEkraniActivity.this, "ca-app-pub-8855046731536561/3607322183", adRequest,
        new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("Geçiş Reklam", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d("Geçiş Reklam", loadAdError.toString());
                mInterstitialAd = null;
                Toast.makeText(SonucEkraniActivity.this, "Reklam Yüklenemedi", Toast.LENGTH_LONG).show();
            }
        });
    }
}