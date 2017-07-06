package com.surtymar.vbadge.Adapters;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.surtymar.vbadge.Activities.MainActivity;
import com.surtymar.vbadge.R;
import com.surtymar.vbadge.Utils.ImageSaver;

/**
 * Created by Anass on 22/06/2017.
 */

public class VBadgeAdapter extends PagerAdapter {

    private MainActivity mainActivity;

    public VBadgeAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        ViewGroup layout = null;
        /*if( position == 0 ) {


            Bitmap bitmap = new ImageSaver(mainActivity).
                    setFileName("myImage.png").
                    setDirectoryName("images").
                    load();


            layout = (ViewGroup) inflater.inflate(R.layout.qrcode, container, false);
            ImageView qr_code = ((ImageView) layout.findViewById(R.id.qr_code));
            if (qr_code != null)
                qr_code.setVisibility(View.VISIBLE);
            ProgressBar qr_progress = (ProgressBar) layout.findViewById(R.id.qr_progress);
            if(qr_progress != null)
                qr_progress.setVisibility(View.GONE);

            ((ImageView)layout.findViewById(R.id.qr_code)).setImageBitmap(bitmap);
        }
        if( position == 1 )
            layout = (ViewGroup) inflater.inflate(R.layout.nfc, container, false);
        */if( position == 0 )
            layout = (ViewGroup) inflater.inflate(R.layout.fingerprint, container, false);
        if( position == 1 )
            layout = (ViewGroup) inflater.inflate(R.layout.facial, container, false);
        ((ViewPager) container).addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
