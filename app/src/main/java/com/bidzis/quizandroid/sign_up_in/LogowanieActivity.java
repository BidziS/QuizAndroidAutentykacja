package com.bidzis.quizandroid.sign_up_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bidzis.quizandroid.AppController;
import com.bidzis.quizandroid.MenuActivity;
import com.bidzis.quizandroid.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class LogowanieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        final Button btZaloguj = (Button) findViewById(R.id.btZalogujLogowanieActivity);
        final EditText etNick = (EditText) findViewById(R.id.etNickLoginActivity);
        final EditText etHaslo = (EditText) findViewById(R.id.etHasloLogowanieActivity);

        btZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.ip) + "/quizAndroid/uzytkownicy/uzytkownikLogowanie";
                String userLoginString = "{\n" +
                        "  \"nick\": \"string\",\n" +
                        "  \"haslo\": \"string\"\n" +
                        "}";
                JSONObject userLogin = new JSONObject();
                try {
                    userLogin = new JSONObject(userLoginString);
                    userLogin.put("nick", etNick.getText().toString());
                    userLogin.put("haslo", etHaslo.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                zaloguj(userLogin, url);
            }
        });

    }
    public void zaloguj(final JSONObject jsonDane, String url) {

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, url, jsonDane, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(LogowanieActivity.this, MenuActivity.class);
                        try {
                            intent.putExtra("id", response.getString("id"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LogowanieActivity.this.startActivity(intent);

                    }
                },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "Wystąpił błąd",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HttpAuthentication httpA = null;
                try {
                    httpA = new HttpBasicAuthentication(jsonDane.getString("nick"), jsonDane.getString("haslo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpHeaders requestHeaders = new HttpHeaders();
                assert httpA != null;
                requestHeaders.setAuthorization(httpA);
                return requestHeaders.toSingleValueMap();
            }
        };

        AppController.pobierzInstancje().addToRequestQueue(request);

    }



}