package com.bidzis.quizandroid.rozgrywka;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bidzis.quizandroid.R;

public class TrybCzasowyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryb_czasowy);
        final TextView tvCounter = (TextView) findViewById(R.id.tvCounter);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCounter.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvCounter.setText("done!");
            }

        }.start();
    }
}
