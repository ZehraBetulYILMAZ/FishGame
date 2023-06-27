package com.ZehraBetulJava.fishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView resim;
    private Animation animation;
    private AppCompatButton buttonBasla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resim = findViewById(R.id.imageView1);
        buttonBasla = findViewById(R.id.buttopnBasla);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        resim.setAnimation(animation);

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OyunEkraniActivity.class);
                startActivity(intent);
            }
        });
    }
}