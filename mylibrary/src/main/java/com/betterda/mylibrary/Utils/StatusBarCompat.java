package com.betterda.mylibrary.Utils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Set coloring status bar
 * Created by Administrator on 2016/11/29.
 */

public class StatusBarCompat {

    public static void setStatusBar5(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0 and above
            // In some cases, you need to clear the transparent flag first
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = activity.getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //Set the color of the status bar
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 to 5.0
            //4.4 This is just to make the status bar transparent, the layout will be derived from the status bar
         /*   WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
*/
        }
    }

    public static  void setStatusBar4(Activity activity,int color) {
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
            //Let the layout not derived to the status bar
            ViewGroup mContentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {

                ViewCompat.setFitsSystemWindows(mChildView, true);
            }
            //Add layout
            addStatusBarView(activity,color,mContentView);
        }*/
    }

    /**
     * Add layout to status bar
     * @param color
     * @param viewGroup
     */
    public static  void addStatusBarView(Activity activity,int color,ViewGroup viewGroup) {
        View view = new View(activity);
        view.setBackgroundColor(activity.getResources().getColor(color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusHeight(activity));
        view.setLayoutParams(params);
        viewGroup.addView(view);


    }

    /**
     *Get the status bar height
     *
     * @param activity
     * @return
     */
    public static int statusHeight(Activity activity) {

        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
