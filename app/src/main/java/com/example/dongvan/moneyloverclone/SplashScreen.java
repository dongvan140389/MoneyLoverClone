package com.example.dongvan.moneyloverclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class SplashScreen extends AppCompatActivity {

    WaveLoadingView waveLoadingView;
    TextView txtPhanTram;
    int count =0;
    Handler handlerUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        waveLoadingView = (WaveLoadingView) findViewById(R.id.waveloading);
        txtPhanTram = (TextView) findViewById(R.id.txtPhanTram);
        waveLoadingView.setProgressValue(count);
        txtPhanTram.setText("0%");

        handlerUpdate = new Handler();

    }

    private void updateData(){
        handlerUpdate.postDelayed(displayData,1000);
    }

    private Runnable displayData = new Runnable() {
        @Override
        public void run() {
            count = count + 20;
            waveLoadingView.setProgressValue(count);
            txtPhanTram.setText(String.format("%d%%",count-20));
            if(count<=100){
                updateData();
            }
            else{
                Intent iTrangChu = new Intent(SplashScreen.this, AfterSplashActivity.class);
                startActivity(iTrangChu);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onStop();
        handlerUpdate.removeCallbacks(displayData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
}
