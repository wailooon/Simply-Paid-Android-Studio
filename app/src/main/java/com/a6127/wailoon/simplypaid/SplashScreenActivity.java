package com.a6127.wailoon.simplypaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar)findViewById(R.id.firstpage_progressBar);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                try{
                    sleep(6000);
                    Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(myIntent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
