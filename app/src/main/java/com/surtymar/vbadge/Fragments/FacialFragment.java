package com.surtymar.vbadge.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kairos.KairosListener;
import com.surtymar.vbadge.KairosSDKsrc.Kairos;
import com.surtymar.vbadge.R;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class FacialFragment extends Fragment {

    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.facial_fragment, container, false);

        KairosListener listener = new KairosListener() {

            @Override
            public void onSuccess(String response) {
                Log.d("KAIROS DEMO", response);
                Toast.makeText(getContext(), "-----Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String response) {
                Log.d("KAIROS DEMO", response);
                Toast.makeText(getContext(), "-----Failed", Toast.LENGTH_SHORT).show();
            }
        };

        /* * * instantiate a new kairos instance * * */
        Kairos myKairos = new Kairos();

        /* * * set authentication * * */
        String app_id = getResources().getString(R.string.app_id);
        String api_key = getResources().getString(R.string.api_key);
        myKairos.setAuthentication(getContext(), app_id, api_key);

        try {
            String image = "http://media.kairos.com/liz.jpg";
            String imageToCompare = "https://image.ibb.co/c8M8pa/picture.jpg";
            String subjectId = "Elizabeth";
            String galleryId = "friends";
            myKairos.detect(image, null, null, listener);

            myKairos.enroll(image, subjectId, galleryId, null, null, null, listener);

            myKairos.recognize(imageToCompare, galleryId, null, null, null, null, listener);

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return v;
    }
}