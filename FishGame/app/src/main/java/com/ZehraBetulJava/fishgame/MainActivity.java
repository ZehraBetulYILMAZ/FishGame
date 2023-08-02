package com.ZehraBetulJava.fishgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private ImageView resim;
    private Animation animation;
    private AppCompatButton buttonBasla;
    private AdView banner;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resim = findViewById(R.id.imageView1);
        buttonBasla = findViewById(R.id.buttopnBasla);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        resim.setAnimation(animation);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        gecisReklamYukle();
         banner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OyunEkraniActivity.class);
                startActivity(intent);
                if(mInterstitialAd != null){
                    mInterstitialAd.show(MainActivity.this);
                    gecisReklamYukle();
                }
            }
        });
    }
    public void gecisReklamYukle(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(MainActivity.this,"ca-app-pub-3940256099942544/1033173712", adRequest,
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
                        Toast.makeText(MainActivity.this,"Reklam Yüklenemedi",Toast.LENGTH_LONG).show();
                    }
                });
    }
}