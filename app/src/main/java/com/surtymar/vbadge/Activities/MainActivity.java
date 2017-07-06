package com.surtymar.vbadge.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.surtymar.vbadge.Fragments.VBadgeFragment;
import com.surtymar.vbadge.MyApplication;
import com.surtymar.vbadge.R;

public class MainActivity extends AppCompatActivity {

    public String bodyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication)getApplication()).setFragment(MainActivity.this, new VBadgeFragment(),R.id.fragment_container);
    }
}
