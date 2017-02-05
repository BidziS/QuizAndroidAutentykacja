package com.bidzis.quizandroid;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Bidzis on 1/17/2017.
 */

public class AppController extends Application {

    public static final String TAG = "VolleyPatterns";

    private RequestQueue requestQueue;

    private static AppController appController;

    @Override
    public void onCreate() {
        super.onCreate();

        appController = this;
    }

    public static synchronized AppController pobierzInstancje() {
        return appController;
    }


    DefaultHttpClient httpClient;
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            httpClient = new DefaultHttpClient();

            CookieStore cookieStore = httpClient.getCookieStore();

            requestQueue = Volley.newRequestQueue(this, new HttpClientStack(httpClient));
        }
        return requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}
