package com.bidzis.quizandroid.rozgrywka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bidzis.quizandroid.R;

public class WybierzTrybActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybierz_tryb);

        Intent intent = getIntent();
        final String idUzytkownika = intent.getStringExtra("idUzytkownika");
        final String idTrybNormalny = "1";
        final String idTrybCzasowy = "2";

        final Button normalnyTryb = (Button) findViewById(R.id.btTrybNormalnyWybierzTrybActivity);
        final Button czasowyTryb = (Button) findViewById(R.id.btTrybCzasowyWybierzTrybActivity);

        normalnyTryb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WybierzTrybActivity.this, WybierzKategorieActivity.class);
                intent.putExtra("idUzytkownika",idUzytkownika);
                intent.putExtra("idTryb",idTrybNormalny);
                WybierzTrybActivity.this.startActivity(intent);
                finish();

            }
        });
        czasowyTryb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WybierzTrybActivity.this, WybierzKategorieActivity.class);
                intent.putExtra("idUzytkownika",idUzytkownika);
                intent.putExtra("idTryb",idTrybCzasowy);
                WybierzTrybActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}
