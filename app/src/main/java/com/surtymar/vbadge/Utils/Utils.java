package com.surtymar.vbadge.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.surtymar.vbadge.Activities.MainActivity;
import com.surtymar.vbadge.Beans.Coordinates;
import com.surtymar.vbadge.Beans.Section;
import com.surtymar.vbadge.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.realm.Realm;

/**
 * Created by Anass on 28/06/2017.
 */

public class Utils {

    public static void createQR(View view){
        MultiFormatWriter mfw =new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = mfw.encode(textToGenerate(view), BarcodeFormat.QR_CODE,100,100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView) view.findViewById(R.id.qrcode)).setImageBitmap(bitmap);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }

    public static String textToGenerate(View v){
        String qrcodeToGenerate = "Nom et Prénom : "+((TextView)v.findViewById(R.id.vbadge_name)).getText().toString().trim()+ "\n"
                +"Etat de badge : "+((TextView)v.findViewById(R.id.vbadge_state)).getText().toString().trim()+ "\n"
                +"Date d'entrée : "+((TextView)v.findViewById(R.id.date_in)).getText().toString().trim()+ "\n"
                +"Date de sortie : "+((TextView)v.findViewById(R.id.date_out)).getText().toString().trim();
        return qrcodeToGenerate;
    }

    public static String retreiveJsonFromAssetsFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();

        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;


        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);//,"ISO-8859-1");
            input = new BufferedReader(isr);
            String line = "";

            while ((line = input.readLine()) != null) {
                returnString.append(line);
                //Log.e(" File content :  "," ::  "+returnString);


            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public static void DisplayMapPopup(final  View v, final MainActivity mainActivity) {
        final Realm realm = Realm.getDefaultInstance();
        //final EditText localisation = (EditText) v.findViewById(R.id.location);
        v.findViewById(R.id.openMapDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GoogleMap googleMap;
//                final Animation slide_out_bottom = AnimationUtils.loadAnimation(mainActivity, R.anim.slide_out_bottom);
//                final Animation slide_in_bottom = AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_bottom);
                final Dialog dialog = new Dialog(mainActivity);
                //mMapView = (MapView) dialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(mainActivity);

                MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                if (mMapView != null) {
                    Log.e("mMapView != null","====true");
                    mMapView.onCreate(dialog.onSaveInstanceState());
                    mMapView.onResume();// needed to get the map to display immediately
                    googleMap = mMapView.getMap();
                    Coordinates myLocation = realm.where(Coordinates.class).equalTo("id", 1).findFirst();
                    MarkerOptions marker0;
                    // latitude and longitude
                    if (googleMap != null) {
                        Log.e("googleMap != null","====true");
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        dialog.setContentView(R.layout.dialogmap);
                        dialog.show();

                        /*if (dialog.findViewById(R.id.bar_save) != null) {
                            dialog.findViewById(R.id.bar_save).setVisibility(View.VISIBLE);
                            dialog.findViewById(R.id.bar_save).setAnimation(slide_in_bottom);
                            dialog.findViewById(R.id.bar_save).findViewById(R.id.annuler).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.findViewById(R.id.bar_save).setAnimation(slide_out_bottom);
                                    dialog.findViewById(R.id.bar_save).setVisibility(View.GONE);
                                    localisation.setText(googleMap.getMyLocation().getLatitude() + " " + googleMap.getMyLocation().getLongitude());

                                }
                            });
                            dialog.findViewById(R.id.bar_save).findViewById(R.id.enregistrer).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.findViewById(R.id.bar_save).setAnimation(slide_out_bottom);
                                    dialog.findViewById(R.id.bar_save).setVisibility(View.GONE);
                                    dialog.dismiss();

                                }
                            });
                        }*/
                        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                        UiSettings uiSettings = googleMap.getUiSettings();
                        uiSettings.setMyLocationButtonEnabled(true);
                        uiSettings.setZoomControlsEnabled(true);
                        uiSettings.setCompassEnabled(true);
                        uiSettings.setAllGesturesEnabled(true);
                        uiSettings.setIndoorLevelPickerEnabled(true);
                        uiSettings.setZoomGesturesEnabled(true);
                        uiSettings.setMapToolbarEnabled(true);

                        if (myLocation != null) {

                            double latitude = myLocation.getLatitude();
                            double longitude = myLocation.getLongitude();
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(latitude, longitude)).zoom(16.0F).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            /*googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                                @Override
                                public void onMarkerDragStart(Marker arg0) {
                                    // TODO Auto-generated method stub
                                    Log.e("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                                }

                                @SuppressWarnings("unchecked")
                                @Override
                                public void onMarkerDragEnd(final Marker arg0) {
                                    // TODO Auto-generated method stub
                                    Log.e("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                                    if (dialog.findViewById(R.id.bar_save) != null) {
                                        dialog.findViewById(R.id.bar_save).setVisibility(View.VISIBLE);
                                        dialog.findViewById(R.id.bar_save).setAnimation(slide_in_bottom);
                                        dialog.findViewById(R.id.bar_save).findViewById(R.id.annuler).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.findViewById(R.id.bar_save).setAnimation(slide_out_bottom);
                                                dialog.findViewById(R.id.bar_save).setVisibility(View.GONE);
                                                localisation.setText(arg0.getPosition().latitude + " " + arg0.getPosition().longitude);
                                            }
                                        });
                                        dialog.findViewById(R.id.bar_save).findViewById(R.id.enregistrer).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.findViewById(R.id.bar_save).setAnimation(slide_out_bottom);
                                                dialog.findViewById(R.id.bar_save).setVisibility(View.GONE);
                                                dialog.dismiss();

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onMarkerDrag(Marker arg0) {
                                    // TODO Auto-generated method stub
                                    Log.i("System out", "onMarkerDrag...");
                                }
                            });*/
                            marker0 = new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Infraction");

                            marker0.draggable(true);
                            googleMap.addMarker(marker0);


                        } else
                            Toast.makeText(mainActivity, "Redémarrez votre GPS !", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(mainActivity, "Redémarrez votre GPS !", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public  static  void openMapDialog(final View v, final MainActivity mainActivity) {
        final Realm realm = Realm.getDefaultInstance();
        v.findViewById(R.id.openMapDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final Dialog dialog = new Dialog(mainActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.setContentView(R.layout.dialogmap);

                final GoogleMap googleMap;
                MapsInitializer.initialize(mainActivity);

                MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();// needed to get the map to display immediately
                googleMap = mMapView.getMap();
                String section = ((Spinner)v.findViewById(R.id.vbadge_section))
                        .getSelectedItem().toString();
                Coordinates myLocation = realm.where(Section.class)
                        .equalTo("name",section).findFirst().getCoordinates();
                final MarkerOptions marker0;
                // latitude and longitude

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setCompassEnabled(true);
                uiSettings.setAllGesturesEnabled(true);
                uiSettings.setIndoorLevelPickerEnabled(true);
                uiSettings.setZoomGesturesEnabled(true);
                uiSettings.setMapToolbarEnabled(true);

                if(myLocation != null)
                {
                    double latitude = myLocation.getLatitude();
                    double longitude = myLocation.getLongitude();
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude)).zoom(16.0F).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    marker0 = new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("Secteur")
                            .draggable(false);
                    googleMap.addMarker(marker0);
                    dialog.show();
                    }
                }
        });
    }
}
