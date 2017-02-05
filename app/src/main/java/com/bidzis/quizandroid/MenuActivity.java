package com.bidzis.quizandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bidzis.quizandroid.ranking.RankingGlobalnyActivity;
import com.bidzis.quizandroid.ranking.RankingWybierzActivity;
import com.bidzis.quizandroid.rozgrywka.WybierzTrybActivity;
import com.bidzis.quizandroid.sign_up_in.LogowanieActivity;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        final String idUzytkownika = intent.getStringExtra("id");

        final Button btGraj = (Button) findViewById(R.id.btGrajMenuActivity);
        final Button btWyjsc = (Button) findViewById(R.id.btWyjscieMenuActivity);
        final Button btRanking = (Button) findViewById(R.id.btRankingMenuActivity);


        btGraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, WybierzTrybActivity.class);
                intent.putExtra("idUzytkownika",idUzytkownika);
                MenuActivity.this.startActivity(intent);
                finish();
            }
        });

        btWyjsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(MenuActivity.this, LogowanieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
        btRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RankingWybierzActivity.class);
                intent.putExtra("id",idUzytkownika);
                MenuActivity.this.startActivity(intent);
                finish();
            }
        });


    }

}
