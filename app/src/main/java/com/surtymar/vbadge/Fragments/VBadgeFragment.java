package com.surtymar.vbadge.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.surtymar.vbadge.Activities.MainActivity;
import com.surtymar.vbadge.Adapters.VBadgeAdapter;
import com.surtymar.vbadge.R;


public class VBadgeFragment extends Fragment {

    private View v;
    private FragmentStatePagerAdapter adapter;
    private ViewPager viewPager;
    private LinearLayout btnPrev, btnNext;
    private TextView right_title, left_title;
    private int[] layouts = {0,0};

    public VBadgeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_vbadge, container, false);

        btnPrev = (LinearLayout) v.findViewById(R.id.btn_prev);
        btnNext = (LinearLayout) v.findViewById(R.id.btn_next);
        right_title = (TextView) v.findViewById(R.id.right_title);
        left_title = (TextView) v.findViewById(R.id.left_title);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

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



    // TODO: Rename method, update argument and hook method into UI event

}
