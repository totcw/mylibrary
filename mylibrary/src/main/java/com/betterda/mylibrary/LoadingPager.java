package com.betterda.mylibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.mylibrary.view.LoadingView2;


/**
 *
 * Created by Administrator on 2016/5/11.
 */
public class LoadingPager extends FrameLayout {
   private LoadingView2 loadview_pager;

    private FrameLayout frame_error, frame_empty;
    private TextView tv_empty,tv_error;
    private ImageView iv_empty,iv_error;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.loading_pager, this);
         loadview_pager = (LoadingView2) findViewById(R.id.loadview_pager);
        frame_empty = (FrameLayout) findViewById(R.id.frame_empty);
        frame_error = (FrameLayout) findViewById(R.id.frame_error);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_error = (TextView) findViewById(R.id.page_bt);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        iv_error = (ImageView) findViewById(R.id.iv_error);

    }

    /**
     *
     */
    public void setTitle(String title) {
        if (null != tv_empty) {
            tv_empty.setText(title);
        }
    }

    /**
     *
     *
     * @param listener
     */
    public void setonEmptyClickListener(OnClickListener listener) {
        if (frame_empty != null) {
            frame_empty.setOnClickListener(listener);
        }
    }

    public void setonErrorClickListener(OnClickListener listener) {
        if (frame_error != null) {
            frame_error.setOnClickListener(listener);
        }
    }

    public void hide() {
        if (null != frame_empty && null != frame_error && loadview_pager != null) {
            frame_empty.setVisibility(View.INVISIBLE);
            frame_error.setVisibility(View.INVISIBLE);
            loadview_pager.setVisibility(View.INVISIBLE);
            loadview_pager.stopAnim();
        }
    }

    public void setEmptyVisable() {
        hide();
        if (null != frame_empty) {

            frame_empty.setVisibility(View.VISIBLE);
        }
    }
    public void setErrorVisable() {
        hide();
        if (null != frame_error) {

            frame_error.setVisibility(View.VISIBLE);
        }
    }
    public void setLoadVisable() {
        hide();
        if (null != loadview_pager) {

            loadview_pager.setVisibility(View.VISIBLE);
            loadview_pager.startAnim();
        }
    }

    /**
     *
     * @param resid
     */
    public void setEmptyBackground(int resid) {
        if (iv_empty != null) {
            iv_empty.setBackgroundResource(resid);
        }
    }

    public void setErrorBackground(int resid) {
        if (iv_error != null) {
            iv_error.setBackgroundResource(resid);
        }
    }

    public void setLoadBackground(int resid) {
        loadview_pager.setAnimBackgroundResrouse(resid);
    }

    /**
     *
     */

    public void setEmptyText(String string) {
        if (tv_empty != null) {
            tv_empty.setText(string);
        }
    }

    public void setErrorText(String string) {
        if (tv_error != null) {
            tv_error.setText(string);
        }
    }
}
