package com.bidzis.quizandroid.ranking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bidzis.quizandroid.AppController;
import com.bidzis.quizandroid.R;
import com.bidzis.quizandroid.ranking.Punkty.PunktyAdapter;
import com.bidzis.quizandroid.ranking.Punkty.PunktyClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankingLokalnyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_lokalny);

        final Button btNormalny = (Button) findViewById(R.id.btTrybNormalnyRankingLokalny);
        final Button btCzasowy  = (Button) findViewById(R.id.btTrybCzasowyRankingLokalny);

        Intent intent = getIntent();

        final String idUzytkownika = intent.getStringExtra("id");

        final RequestQueue requestQueue = AppController.pobierzInstancje().getRequestQueue();
        String url = getString(R.string.ip)+"/quizAndroid/punkty/pobierzPoUzytkownikuITrybie/"+"1"+","+idUzytkownika;
        pobierzListe(requestQueue,url);
        btNormalny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.ip)+"/quizAndroid/punkty/pobierzPoUzytkownikuITrybie/"+"1"+","+idUzytkownika;;
                pobierzListe(requestQueue,url);
            }
        });
        btCzasowy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.ip)+"/quizAndroid/punkty/pobierzPoUzytkownikuITrybie/"+"2"+","+idUzytkownika;;
                pobierzListe(requestQueue,url);
            }
        });


    }

    public void pobierzListe(RequestQueue requestQueue, String url){

        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray[] jsonArray = {null};
                        jsonArray[0] = response;
                        ArrayList<PunktyClass> kategorieArray = new ArrayList<>();
                        int len = jsonArray[0].length();
                        for (int i=0;i<len;i++){
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                                JSONObject uzytkownik = jsonObject.getJSONObject("uzytkownicy");
                                kategorieArray.add(i,new PunktyClass((int)jsonObject.get("id"),uzytkownik.getString("nick"),(int)jsonObject.get("punkty"),i+1));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final ListView listview = (ListView) findViewById(R.id.wybierzKategorielistView);

                        final PunktyAdapter punktyAdapter = new PunktyAdapter(RankingLokalnyActivity.this,kategorieArray);

                        listview.setAdapter(punktyAdapter);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Bląd serwera",
                                Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(request);


    }
}
