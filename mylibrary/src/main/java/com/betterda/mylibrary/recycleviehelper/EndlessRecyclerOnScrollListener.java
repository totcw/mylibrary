package com.betterda.mylibrary.recycleviehelper;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;




/**
 *
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    private Activity mContext;

    /**
     *
     */
    protected LayoutManagerType layoutManagerType;

    /**
     *
     */
    private int[] lastPositions;

    /**
     *
     */
    private int lastVisibleItemPosition;

    /**
     *
     */
    private int currentScrollState = 0;
    private int count;



    public EndlessRecyclerOnScrollListener(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();


                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }


    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        //judge data is over screen
        int firstCompletelyVisibleItemPosition = 0;
        int lastCompletelyVisibleItemPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            firstCompletelyVisibleItemPosition =  ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstCompletelyVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            lastCompletelyVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }

        int vieable = lastCompletelyVisibleItemPosition - firstCompletelyVisibleItemPosition;
        count = layoutManager.getItemCount();
        if (count <= 0) {
            return;
        }


        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
            if (vieable + 1 < count) {//show footer
                show(true);
            } else {

                show(false);
            }
            onLoadNextPage(recyclerView);
        }
    }

    /**
     * get max data
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }


    @Override
    public void onLoadNextPage(View view) {

    }

    @Override
    public void show(boolean isShow) {

    }


    public static enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }


}
