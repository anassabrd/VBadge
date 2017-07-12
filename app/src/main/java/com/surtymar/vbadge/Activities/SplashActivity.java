package com.surtymar.vbadge.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.surtymar.vbadge.Beans.Coordinates;
import com.surtymar.vbadge.Beans.Section;
import com.surtymar.vbadge.Beans.Visitor;
import com.surtymar.vbadge.R;
import com.surtymar.vbadge.Utils.ConnectivityCheck;
import com.surtymar.vbadge.Utils.Utils;

import org.json.JSONObject;


import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {


    private Realm realm;
    private String url = "https://api.myjson.com/bins/felpb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConnectivityCheck cc = new ConnectivityCheck(getApplicationContext());

        if (cc.isConnected()) {
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new parseJson(response).execute();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VolleyError", "Error:" + error.getMessage());
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }

    public class parseJson extends AsyncTask<Void, Integer, Void>{

        JSONObject jsonObject = new JSONObject();

        parseJson(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        @Override
        protected Void doInBackground(Void... objects) {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateObjectFromJson(Visitor.class, jsonObject);
                }
            });

            return null;
        }

//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(Visitor.class).findAll().deleteAllFromRealm();
                    realm.where(Section.class).findAll().deleteAllFromRealm();
                    realm.where(Coordinates.class).findAll().deleteAllFromRealm();
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
