package com.surtymar.vbadge;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.surtymar.vbadge.Activities.MainActivity;


/**
 * Created by Anass on 27/04/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//        Realm.setDefaultConfiguration(config);

    }

    public void setFragment(FragmentActivity fragmentActivity, Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(layout_id, fm, ((MainActivity) fragmentActivity).bodyFragment )
                .addToBackStack( ((MainActivity) fragmentActivity).bodyFragment )
                .commit();
    }

    public void refreshFragment(FragmentActivity fragmentActivity,Fragment fm){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .detach(fm)
                .attach(fm)
                /*.addToBackStack( ((MainActivity) fragmentActivity).bodyFragment )*/
                .commit();
    }

    public void setUpFragment(FragmentActivity fragmentActivity,Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(layout_id, fm,  ((MainActivity) fragmentActivity).bodyFragment )
                .addToBackStack( ((MainActivity) fragmentActivity).bodyFragment )
                .commit();
    }

    public void setFrag(FragmentActivity fragmentActivity,Fragment fm, int layout_id){
        ((MainActivity) fragmentActivity).bodyFragment  = fm.getClass().getSimpleName();
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
                .replace(layout_id, fm,  ((MainActivity) fragmentActivity).bodyFragment )
                .commit();                //.addToBackStack( ((MainActivity) fragmentActivity).bodyFragment )

    }
}
