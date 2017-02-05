package com.bidzis.quizandroid.ranking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bidzis.quizandroid.R;

public class RankingWybierzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_wybierz);

        Intent intent = getIntent();

        final String idUzytkownika = intent.getStringExtra("id");

        final Button btOgolny = (Button) findViewById(R.id.btOgolnyRanking);
        final Button btLokalny = (Button) findViewById(R.id.btMojRanking);


        btLokalny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingWybierzActivity.this, RankingLokalnyActivity.class);
                intent.putExtra("id",idUzytkownika);
                RankingWybierzActivity.this.startActivity(intent);
            }
        });

        btOgolny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingWybierzActivity.this, RankingGlobalnyActivity.class);
                RankingWybierzActivity.this.startActivity(intent);
            }
        });
    }
}
