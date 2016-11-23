package com.betterda.mylibrary.recycleviehelper;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.mylibrary.view.LoadingFooter;

import java.util.List;


/**
 * Created by cundong on 2015/11/9.
 *
 *
 *
 *
 */
public class RecyclerViewStateUtils {
    
    /**
     * setting headerAndFooterAdapterFooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param state         FooterView State
     * @param errorListener FooterView is Error's click event
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener) {

        if(instance==null || instance.isFinishing()||recyclerView==null) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();


        if (outerAdapter == null ) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        LoadingFooter footerView;


        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state,true);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

        } else {

            footerView = new LoadingFooter(instance);
            footerView.setState(state,true);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);

        }
    }

    /**
     *
     *
     * @param recyclerView
     */
    public static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return null;
    }





  /*
     *
     * @param list rv
     * @param rv_query
     * @param activity
     * @param size
     */
    public static   void setLoad(List list, RecyclerView rv_query, Activity activity,int size) {


        if (list != null && rv_query != null && activity != null) {
            if (list.size() >= size) {

                RecyclerViewStateUtils.setFooterViewState(activity,
                        rv_query,  LoadingFooter.State.Normal, null);

            } else {

                RecyclerViewStateUtils.setFooterViewState(activity,
                        rv_query, LoadingFooter.State.TheEnd, null);

            }
        }

    }


    /**
     *
     * @param recyclerView
     * @param state
     * @param errorListener
     */
    public static void change(RecyclerView recyclerView, LoadingFooter.State state, View.OnClickListener errorListener) {

        if(recyclerView==null) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();


        if (outerAdapter == null ) {
            return;
        }
        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            LoadingFooter footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state,false);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }


        }
    }

    /**
     * judge is show footer
     * @param isShow
     * @param list
     * @param recyclerView
     * @param activity
     * @param size count of page
     */
    public static   void show(boolean isShow, List list, RecyclerView recyclerView, Activity activity,int size) {
        if (isShow) {
            RecyclerViewStateUtils.setLoad(list, recyclerView, activity,size);
        } else {
            RecyclerViewStateUtils.change(recyclerView, LoadingFooter.State.TheEnd, null);
        }
    }

    /**
     * load
     */
    public static void next(Activity activity, RecyclerView recyclerView, nextListener listener) {
        //
        if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {

            RecyclerViewStateUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.Loading, null);

            if (listener != null) {
                listener.load();
            }
        }
    }

    public interface nextListener{
        void load();
    }

}
