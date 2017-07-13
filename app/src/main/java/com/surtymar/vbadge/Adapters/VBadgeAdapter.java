package com.surtymar.vbadge.Adapters;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.surtymar.vbadge.Activities.MainActivity;
import com.surtymar.vbadge.Fragments.FingerprintFragment;
import com.surtymar.vbadge.Fragments.VBadgeFragment;
import com.surtymar.vbadge.MyApplication;
import com.surtymar.vbadge.R;
import com.surtymar.vbadge.Utils.ImageSaver;
import com.surtymar.vbadge.Utils.Utils;

/**
 * Created by Anass on 22/06/2017.
 */

public class VBadgeAdapter extends PagerAdapter {

    private MainActivity mainActivity;
    private int REQUEST_CAMERA = 123;

    public VBadgeAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        ViewGroup layout = null;
        if( position == 0 ) {
            layout = (ViewGroup) inflater.inflate(R.layout.fingerprint, container, false);
            layout.findViewById(R.id.fingerprint).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        ((MyApplication)mainActivity.getApplication()).setFragment(mainActivity, new FingerprintFragment(),R.id.fragment_container);
                    else
                        Toast.makeText(mainActivity,"Votre appareil ne supporte pas l'authentification par empreinte.",Toast.LENGTH_LONG).show();
                }
            });
        }
        if( position == 1 ) {
            final boolean result = Utils.checkPermission(mainActivity);
            layout = (ViewGroup) inflater.inflate(R.layout.facial, container, false);
            layout.findViewById(R.id.facial).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result)
                        mainActivity.startActivityForResult(Utils.cameraIntent(mainActivity), REQUEST_CAMERA);
                }
            });
        }
        container.addView(layout);

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
