package com.bidzis.quizandroid.rozgrywka;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bidzis.quizandroid.AppController;
import com.bidzis.quizandroid.MenuActivity;
import com.bidzis.quizandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrybNormalnyActivity extends AppCompatActivity {


    final ArrayList<PytaniaClass> pytaniaArray = new ArrayList<>();
    final int[] i = {0};
    int punkty = 0;
    public CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryb_normalny);
        final TextView tvPytanie = (TextView) findViewById(R.id.tvPytanieTrybNormalnyActivity);

        final TextView tvCounter = (TextView) findViewById(R.id.tvCounter);

        Intent intent = getIntent();

        String idKategorii = intent.getStringExtra("id");
        String idTryb = intent.getStringExtra("idTryb");
        String idUzytkownika = intent.getStringExtra("idUzytkownika");
        //final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.ip)+"/quizAndroid/pytania/losujPoKategorii/"+idKategorii;

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCounter.setText(String.valueOf(millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

            }

        };


        pobierzListePytan(url, tvPytanie,pytaniaArray,idTryb, idUzytkownika,tvCounter);


    }
    @Override
    public void onBackPressed()
    {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Czy napewno chcesz wyjść? Stan rozgrywki zosatnie utracony.")
                .setTitle("Uwaga");

        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(TrybNormalnyActivity.this, MenuActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void pobierzListePytan(String url, final TextView tvPytanie, final ArrayList<PytaniaClass> pytaniaArray, final String idTryb, final String idUzytkownika, final TextView tvCounter){

        final JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray[] jsonArray = {null};
                        jsonArray[0] = response;
                        int len = jsonArray[0].length();
                        for (int j=0;j<len;j++){
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray[0].get(j);
                                pytaniaArray.add(j,new PytaniaClass((int)jsonObject.get("id"),jsonObject.getString("pytanie")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        PytaniaClass pytaniaClass = pytaniaArray.get(0);


                        if ( i[0] < pytaniaArray.size()){
                            final ArrayList<OdpowiedziClass> odpowiedziArray = new ArrayList<>();
                            String urlOdpowiedzi = getString(R.string.ip)+"/quizAndroid/odpowiedzi/pobierzPoPytaniu/"+pytaniaClass.id;
                            pobierzListeOdpowiedzi(urlOdpowiedzi,tvPytanie,odpowiedziArray, idTryb, idUzytkownika, tvCounter);
                        }

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
    public void pobierzListeOdpowiedzi(String url, final TextView tvPytanie, final ArrayList<OdpowiedziClass> odpowiedziClasses, final String idTryb, final String idUzytkownika,  final TextView tvCouner){

        final JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        if(i[0]!= 0){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownTimer.start();
                        PytaniaClass pytaniaClass = pytaniaArray.get(i[0]);
                        tvPytanie.setText(pytaniaClass.pytanie);
                        JSONArray[] jsonArray = {null};
                        jsonArray[0] = response;
                        ArrayList<String> value = new ArrayList<>();
                        int len = jsonArray[0].length();
                        for (int i=0;i<len;i++){
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                                odpowiedziClasses.add(i,new OdpowiedziClass((int)jsonObject.get("id"),jsonObject.getString("odpowiedz"),jsonObject.getBoolean("poprawna")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final ListView listview = (ListView) findViewById(R.id.odpowiedzilistView);
                        final odpowiedziAdapter odpowiedziAdapter = new odpowiedziAdapter(TrybNormalnyActivity.this,odpowiedziClasses);



                        listview.setAdapter(odpowiedziAdapter);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String urlZapiszPkt = getString(R.string.ip)+"/quizAndroid/punkty/zapiszPunkty2";
                                if ( i[0] < pytaniaArray.size()){
                                    final ArrayList<OdpowiedziClass> odpowiedziArray = new ArrayList<>();
                                    i[0]++;
                                    if(i[0] == pytaniaArray.size()){
                                        if(odpowiedziAdapter.data.get(position).poprawna){
                                            view.setBackgroundColor(Color.GREEN);
                                            view.setSelected(true);

                                            punkty += Integer.valueOf(tvCouner.getText().toString());
//                                            punkty += 10;
                                            countDownTimer.cancel();
                                            try {
                                                zapiszPkt(urlZapiszPkt,punkty,idUzytkownika,idTryb);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            MojAlert(idUzytkownika);

                                        }
                                        else{
                                            view.setBackgroundColor(Color.RED);
                                            view.setSelected(true);
                                            i[0] = 0;
                                            if(punkty!= 0) try {
                                                zapiszPkt(urlZapiszPkt,punkty,idUzytkownika,idTryb);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            MojAlert(idUzytkownika);

                                        }
                                        return;
                                    }
                                    PytaniaClass pytaniaClass = pytaniaArray.get(i[0]);
                                    String urlOdpowiedzi = getString(R.string.ip)+"/quizAndroid/odpowiedzi/pobierzPoPytaniu/"+pytaniaClass.id;

                                    if(odpowiedziAdapter.data.get(position).poprawna){
                                        view.setBackgroundColor(Color.GREEN);
                                        view.setSelected(true);
                                        punkty += Integer.valueOf(tvCouner.getText().toString());
                                        punkty += 10;
                                    }
                                    else{
                                        view.setBackgroundColor(Color.RED);
                                        view.setSelected(true);
                                        i[0] = 0;
                                        if(punkty!= 0) try {
                                            zapiszPkt(urlZapiszPkt,punkty,idUzytkownika,idTryb);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        MojAlert(idUzytkownika);

                                    }

                                    pobierzListeOdpowiedzi(urlOdpowiedzi,tvPytanie,odpowiedziArray,idTryb,idUzytkownika, tvCouner);
                                }
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

    public void zapiszPkt(String url, long pkt, String idUzyt, String idTryb) throws JSONException {

        String userLoginString = "{ \"id\": \"0\", \"punkty\": \"0\", \"techDate\": \"2016-11-28T21:35:27.232Z\", \"trybid\": \"1\", \"uzytkownikid\": \"1\"}";

        JSONObject jsonDane =  new JSONObject(userLoginString);
        jsonDane.put("punkty",pkt);
        jsonDane.put("trybid",idTryb);
        jsonDane.put("uzytkownikid",idUzyt);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, url, jsonDane, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "Wystąpił błąd",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

        AppController.pobierzInstancje().addToRequestQueue(request);

    }


    public void MojAlert(final String idUzytkownika){
        AlertDialog alertDialog = new AlertDialog.Builder(TrybNormalnyActivity.this).create();
        alertDialog.setTitle("Koniec");
        alertDialog.setMessage("Uzyskałeś: "+String.valueOf(punkty)+" punktów!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TrybNormalnyActivity.this, MenuActivity.class);
                        intent.putExtra("id",idUzytkownika);
                        TrybNormalnyActivity.this.startActivity(intent);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    class odpowiedziAdapter extends BaseAdapter {

        Context context;
        ArrayList<OdpowiedziClass> data;
        private LayoutInflater inflater = null;

        public odpowiedziAdapter(Context context, ArrayList<OdpowiedziClass> data) {
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
            OdpowiedziClass item = data.get(position);
            if (vi == null)
                vi = inflater.inflate(R.layout.row, null);
            TextView text = (TextView) vi.findViewById(R.id.text);
            text.setText(item.odpowiedz);
            return vi;
        }
    }
    public class PytaniaClass{
        int id;
        String pytanie;

        public PytaniaClass(int id, String pytanie) {
            this.id = id;
            this.pytanie = pytanie;
        }
    }
    public class OdpowiedziClass{
        int id;
        String odpowiedz;
        boolean poprawna;

        public OdpowiedziClass(int id, String odpowiedz, boolean poprawna) {
            this.id = id;
            this.odpowiedz = odpowiedz;
            this.poprawna = poprawna;
        }
    }
}
