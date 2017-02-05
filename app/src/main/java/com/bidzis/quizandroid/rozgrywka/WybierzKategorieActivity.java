package com.bidzis.quizandroid.rozgrywka;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bidzis.quizandroid.AppController;
import com.bidzis.quizandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WybierzKategorieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybierz_kategorie);

        Intent intent = getIntent();
        final String idUzytkownika = intent.getStringExtra("idUzytkownika");
        final String idTryb = intent.getStringExtra("idTryb");


        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.ip)+"/quizAndroid/kategorie/pobierzWszystkie";

        pobierzListe(requestQueue,url,idTryb,idUzytkownika);

    }

    public void pobierzListe(RequestQueue requestQueue, String url, final String idTryb, final String idUzytkownika){


        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray[] jsonArray = {null};
                        jsonArray[0] = response;
                        ArrayList<KategorieClass> kategorieArray = new ArrayList<>();
                        int len = jsonArray[0].length();
                        for (int i=0;i<len;i++){
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                                kategorieArray.add(i,new KategorieClass((int)jsonObject.get("id"),jsonObject.getString("nazwa")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final ListView listview = (ListView) findViewById(R.id.wybierzKategorielistView);

                        final yourAdapter2 adapter2 = new yourAdapter2(WybierzKategorieActivity.this,kategorieArray);


                        listview.setAdapter(adapter2);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String nazwaKategorii = adapter2.data.get(position).nazwa;
                                int idKategoriInt = adapter2.data.get(position).id;
                                String idKatrgorii = String.valueOf(idKategoriInt);
                                Toast.makeText(getApplicationContext(), nazwaKategorii,
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WybierzKategorieActivity.this, TrybNormalnyActivity.class);
                                intent.putExtra("id",idKatrgorii);
                                intent.putExtra("idUzytkownika",idUzytkownika);
                                intent.putExtra("idTryb",idTryb);
                                WybierzKategorieActivity.this.startActivity(intent);
                                finish();
                            }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Bląd serwera",
                                Toast.LENGTH_LONG).show();
                    }
                });
        AppController.pobierzInstancje().addToRequestQueue(request);


    }

    class yourAdapter2 extends BaseAdapter {

        Context context;
        ArrayList<KategorieClass> data;
        private LayoutInflater inflater = null;

        public yourAdapter2(Context context, ArrayList<KategorieClass> data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            KategorieClass item = data.get(position);
            if (vi == null)
                vi = inflater.inflate(R.layout.row, null);
            TextView text = (TextView) vi.findViewById(R.id.text);
            text.setText(item.nazwa);
            return vi;
        }
    }

    public class KategorieClass{
        int id;
        String nazwa;

        public KategorieClass(int id, String nazwa) {
            this.id = id;
            this.nazwa = nazwa;
        }
    }
}
