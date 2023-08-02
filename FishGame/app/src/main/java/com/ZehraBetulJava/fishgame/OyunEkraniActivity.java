package com.ZehraBetulJava.fishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OyunEkraniActivity extends AppCompatActivity {

    private ConstraintLayout cl;
    private TextView textViewSkor;
    private TextView textViewBasla;
    private ImageView anakarakter;
    private ImageView sariDikenliBalik;
    private ImageView kopekBaligi;
    private ImageView pembeBalik;
    private ImageView turkuazBalik;
    private ImageView cizgiliBalik;

    //Pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int sariDikenliBalikX;
    private int sariDikenliBalikY;
    private int kopekBaligiX;
    private int kopekBaligiY;
    private int pembeBalikX;
    private int pembeBalikY;
    private int turkuazBalikX;
    private int turkuazBalikY;
    private int cizgiliBalikX;
    private int cizgiliBalikvY;

    //Boyutlara
    private int ekranGenisligi;
    private int ekranYukseligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;

    //Hızlar
    private int anakarakterHiz;
    private int pembeBalikHiz;
    private int turkuazBalikHiz;
    private int cizgiliBalikHiz;
    private int kopekBaligikHiz;
    private int sariDikenliBalikHiz;

    //Kontroller
    private boolean dokunmaKontrol = false;
    private boolean baslangicKontrol = false;

    private int skor = 0 ;

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);

        cl = findViewById(R.id.cl);
        anakarakter = findViewById(R.id.anaKarakter);
        sariDikenliBalik = findViewById(R.id.dikenliSariBalik);
        kopekBaligi = findViewById(R.id.kopekBaligi);
        turkuazBalik = findViewById(R.id.turkuazBalik);
        pembeBalik = findViewById(R.id.pembeBalik);
        cizgiliBalik = findViewById(R.id.cizgiliBalik);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewBasla = findViewById(R.id.textViewBasla);

        sariDikenliBalik.setX(-80);
        sariDikenliBalik.setY(-80);
        kopekBaligi.setX(-80);
        kopekBaligi.setY(-80);
        cizgiliBalik.setX(-80);
        cizgiliBalik.setY(-80);
        pembeBalik.setX(-80);
        pembeBalik.setY(-80);
        turkuazBalik.setX(-80);
        turkuazBalik.setY(-80);

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (baslangicKontrol) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent","Ekrana dokunuldu");
                        dokunmaKontrol = true;

                    }

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent","Ekranı bıraktı");
                        dokunmaKontrol = false;
                    }

                }else{

                    baslangicKontrol = true ;

                    textViewBasla.setVisibility(View.INVISIBLE);

                    anakarakterX = (int) anakarakter.getX();
                    anakarakterY = (int) anakarakter.getY();

                    anakarakterGenisligi = anakarakter.getWidth();
                    anakarakterYuksekligi = anakarakter.getHeight();
                    ekranGenisligi = cl.getWidth();
                    ekranYukseligi = cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    anakarakterHareketEttirme();
                                    cisimlerinHareketEttir();
                                    carpismaKontrol();
                                }
                            });
                        }
                    },0,20);
                    
                }

                return true;
            }
        });
    }

    private void carpismaKontrol() {

        if (cisimlerinCarpmaKontrolu(pembeBalikX,pembeBalikY,pembeBalik)){
            skor+=25;
            pembeBalikX=-10;
        }
        if (cisimlerinCarpmaKontrolu(turkuazBalikX,turkuazBalikY,turkuazBalik)){

            skor+=10;
            turkuazBalikX=-10;
        }
        if (cisimlerinCarpmaKontrolu(cizgiliBalikX,cizgiliBalikvY,cizgiliBalik)){

            skor+=50;
            cizgiliBalikX=-10;
        }

        if (cisimlerinCarpmaKontrolu(sariDikenliBalikX,sariDikenliBalikY,sariDikenliBalik)){

            skor+=100;
            sariDikenliBalikX=-10;
        }

        if (cisimlerinCarpmaKontrolu(kopekBaligiX,kopekBaligiY,kopekBaligi)){

            //Timer durdur.
            timer.cancel();
            timer=null;

            Intent intent = new Intent(OyunEkraniActivity.this,SonucEkraniActivity.class);
            intent.putExtra("skor",skor);
            startActivity(intent);
            finish();

        }

        textViewSkor.setText(String.valueOf(skor));
    }
    private boolean cisimlerinCarpmaKontrolu(int X, int Y, ImageView cisim){
        int cisimMerkezX = X + cisim.getWidth()/2;
        int cisimMerkezY = Y + cisim.getHeight()/2;

        if (0 <= cisimMerkezX && cisimMerkezX<= anakarakterGenisligi
                && anakarakterY <= cisimMerkezY && cisimMerkezY <= anakarakterY+anakarakterYuksekligi){
            return true;
        }
        else
            return false;
    }

    private void cisimlerinHareketEttir() {
        sariDikenliBalikHiz= Math.round(ekranGenisligi/45);//760 / 60  = 13
        kopekBaligikHiz = Math.round(ekranGenisligi/60);//760 / 60  = 13
        turkuazBalikHiz= Math.round(ekranGenisligi/180);//760 / 30  = 26
        pembeBalikHiz =Math.round(ekranGenisligi/120);//760 / 60  = 13
        cizgiliBalikHiz=  Math.round(ekranGenisligi/60);//760 / 60  = 13

        kopekBaligiX-=kopekBaligikHiz;

        if (kopekBaligiX < 0 ){
            kopekBaligiX = ekranGenisligi + 20 ;
            kopekBaligiY = (int) Math.floor(Math.random() * ekranYukseligi);
        }

        kopekBaligi.setX(kopekBaligiX);
        kopekBaligi.setY(kopekBaligiY);

        sariDikenliBalikX-=sariDikenliBalikHiz;

        if (sariDikenliBalikX < 0 ){
            sariDikenliBalikX = ekranGenisligi + 20 ;
            sariDikenliBalikY = (int) Math.floor(Math.random() * ekranYukseligi);
        }

        sariDikenliBalik.setX(sariDikenliBalikX);
        sariDikenliBalik.setY(sariDikenliBalikY);


        turkuazBalikX-=turkuazBalikHiz;

        if (turkuazBalikX < 0 ){
            turkuazBalikX = ekranGenisligi + 20 ;
            turkuazBalikY = (int) Math.floor(Math.random() * ekranYukseligi);
        }

        turkuazBalik.setX(turkuazBalikX);
        turkuazBalik.setY(turkuazBalikY);

        pembeBalikX-=pembeBalikHiz;

        if (pembeBalikX < 0 ){
            pembeBalikX = ekranGenisligi + 20 ;
            pembeBalikY = (int) Math.floor(Math.random() * ekranYukseligi);
        }

        pembeBalik.setX(pembeBalikX);
        pembeBalik.setY(pembeBalikY);

        cizgiliBalikX-=cizgiliBalikHiz;

        if (cizgiliBalikX < 0 ){
            cizgiliBalikX = ekranGenisligi + 20 ;
            cizgiliBalikvY = (int) Math.floor(Math.random() * ekranYukseligi);
        }

        cizgiliBalik.setX(cizgiliBalikX);
        cizgiliBalik.setY(cizgiliBalikvY);
    }

    private void anakarakterHareketEttirme() {
        anakarakterHiz = Math.round(ekranYukseligi/60);//1280 / 60  = 20

        if(dokunmaKontrol){
            anakarakterY-=anakarakterHiz;
        }else{
            anakarakterY+=anakarakterHiz;
        }

        if(anakarakterY <= 0){
            anakarakterY = 0 ;
        }

        if(anakarakterY >= ekranYukseligi - anakarakterYuksekligi){
            anakarakterY = ekranYukseligi - anakarakterYuksekligi;
        }

        anakarakter.setY(anakarakterY);
    }

}