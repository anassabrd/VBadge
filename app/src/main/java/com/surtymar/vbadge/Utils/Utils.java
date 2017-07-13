package com.surtymar.vbadge.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

    public static File createImage(MainActivity mainActivity) {

        boolean result = Utils.checkPermission(mainActivity);
        boolean success = true;

        String root = Utils.getFirstWritableDirectory().toString();
        Log.e("root", "" + root);
        File myDir = new File(root + "/.VBADGE_IMAGES");
        if(result) {
            if (!myDir.exists())
                success = myDir.mkdirs();
        }
        if(success)
            Log.e("Success","folder created");
        else
            Log.e("Failed","folder not created");

        final File destination = new File(myDir,System.currentTimeMillis() + ".jpg");

        return destination;

        /*try {
            Log.e("URL",""+url);
            FileOutputStream out = new FileOutputStream(destination);
            Bitmap bm = Glide
                    .with(mainActivity)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // Width and height
                    .get();

            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static File getFirstWritableDirectory() {
        File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (file1.exists()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else if (file2.exists()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else if (file3.exists()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return Environment.getExternalStorageDirectory();
        }
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

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static Intent cameraIntent(MainActivity activity){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null){
            File photoFile = null;
            photoFile = createImage(activity);
            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(activity, activity.getPackageName()+".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
        }
        return takePictureIntent;
    }
}
