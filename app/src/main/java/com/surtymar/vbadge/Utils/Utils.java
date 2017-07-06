package com.surtymar.vbadge.Utils;

import android.widget.LinearLayout;

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
}
