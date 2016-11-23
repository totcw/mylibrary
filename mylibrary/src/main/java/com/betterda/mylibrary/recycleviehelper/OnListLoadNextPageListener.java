package com.betterda.mylibrary.recycleviehelper;

import android.view.View;

/**
 * Created by cundong on 2015/10/9.
 * RecyclerView/ListView/GridView
 */
public interface OnListLoadNextPageListener {

    /**
     * load next page
     *
     * @param view RecyclerView/ListView/GridView
     */
    public void onLoadNextPage(View view);
    /**
     * is  show footer
     * @param isShow
     */
    public void  show(boolean isShow);
}
