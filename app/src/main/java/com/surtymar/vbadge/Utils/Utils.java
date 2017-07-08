package com.surtymar.vbadge.Utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.surtymar.vbadge.R;

/**
 * Created by Anass on 28/06/2017.
 */

public class Utils {

    public static void setupLayout(LinearLayout ll){
        if (ll != null){
            if (ll.getWidth() < ll.getMinimumWidth() ){
                if(ll.getParent() != null ){
                    ((LinearLayout)ll.getParent()).setOrientation(LinearLayout.VERTICAL);
                    //((LinearLayout)ll.getParent()).setLayoutGravity();
                }
            }
        }
    }

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
}
