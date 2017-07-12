package com.surtymar.vbadge.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.surtymar.vbadge.Activities.MainActivity;
import com.surtymar.vbadge.Adapters.VBadgeAdapter;
import com.surtymar.vbadge.Beans.Section;
import com.surtymar.vbadge.Beans.Visitor;
import com.surtymar.vbadge.R;
import com.surtymar.vbadge.Utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class VBadgeFragment extends Fragment {

    private Realm realm;
    private Visitor visitor;
    private View v;
    private ViewPager viewPager;
    private LinearLayout btnPrev, btnNext;
    private TextView right_title, left_title;
    private int[] layouts = {0,0};

    public VBadgeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_vbadge, container, false);
        btnPrev = (LinearLayout) v.findViewById(R.id.btn_prev);
        btnNext = (LinearLayout) v.findViewById(R.id.btn_next);
        right_title = (TextView) v.findViewById(R.id.right_title);
        left_title = (TextView) v.findViewById(R.id.left_title);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        visitor = realm.where(Visitor.class).findFirst();

        if (visitor != null) {

            ((TextView) v.findViewById(R.id.vbadge_name)).setText(
                    visitor.getLast_name() + " " + visitor.getFirst_name());

            ((TextView) v.findViewById(R.id.vbadge_state)).setText(" " + visitor.getBadge_state());

            ((TextView) v.findViewById(R.id.date_in)).setText(visitor.getDate_in());

            ((TextView) v.findViewById(R.id.date_out)).setText(visitor.getDate_out());

            final Spinner spinner = (Spinner) v.findViewById(R.id.vbadge_section);
            final List<String> sections = new ArrayList<String>();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (visitor != null) {
                        List<Section> sectionList = realm.where(Section.class).findAll();
                        for (int j = 0; j < sectionList.size(); j++) {
                            sections.add(sectionList.get(j).getName());
                        }
                    }
                    ArrayAdapter<String> dataAdapterLiaison = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, sections);
                    dataAdapterLiaison.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapterLiaison);
                }
            });

            Utils.createImageFile(visitor.getImage_url(), (MainActivity) getActivity());

            Glide.with(getActivity())
                    .load(visitor.getImage_url())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_manager)
                    .into((ImageView) v.findViewById(R.id.pic_vbadge));


        }



        Utils.createQR(v);

        Utils.openMapDialog(v,(MainActivity) getActivity());

        viewPager.setAdapter(new VBadgeAdapter((MainActivity) getActivity()));
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previous = getItem(-1);
                if (previous == 0)
                {

                    right_title.setText("Reconnaissance \n Faciale");
                }
                else  if (previous == 1)
                {
                    right_title.setText("Empreinte\nDigitale");
                    //left_title.setText("Empreinte digitale");
                }

                //  if (previous > layouts.length)

                viewPager.setCurrentItem(previous);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched

                int current = getItem(+1);
                if(current == 1) {
                    //right_title.setText("QR Code");
                    left_title.setText("Empreinte\nDigitale");
                    viewPager.setCurrentItem(current);
                }
                else if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    //launchHomeScreen();
                }
            }
        });
        return v;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if ( position == 0)
            {
                btnNext.setVisibility(View.VISIBLE);
                btnPrev.setVisibility(View.INVISIBLE);
            }
            else if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setVisibility(View.INVISIBLE);
                btnPrev.setVisibility(View.VISIBLE);
            } else  {
                // still pages are left
                btnNext.setVisibility(View.VISIBLE);
                btnPrev.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
