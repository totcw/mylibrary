package com.betterda.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.R;


/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * ListView/GridView/RecyclerView
 */
public class LoadingFooter extends RelativeLayout {

    protected State mState;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private View mJiaZaiView;
    private ProgressBar mLoadingProgress;
    private TextView mLoadingText;

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.sample_common_list_footer, this);
        setOnClickListener(null);

        setState(State.Normal, false);
    }

    public State getState() {
        return mState;
    }

   /* public void setState(State status ) {
        setState(status, true);
    }*/

    /**
     *
     *
     * @param status
     * @param showView
     */
    public void setState(State status, boolean showView) {
      /*  if (mState == status) {
            return;
        }*/
        mState = status;

        switch (status) {

            case Normal:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mJiaZaiView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.jiazai_viewstub);
                    mJiaZaiView = viewStub.inflate();
                } else {
                    mJiaZaiView.setVisibility(VISIBLE);
                }

                mJiaZaiView.setVisibility(showView ? VISIBLE : GONE);

                break;
            case Loading:
                setOnClickListener(null);
                if (mJiaZaiView != null) {
                    mJiaZaiView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                mLoadingProgress.setVisibility(View.VISIBLE);

                break;
            case TheEnd:
                setOnClickListener(null);
                if (mJiaZaiView != null) {
                    mJiaZaiView.setVisibility(GONE);
                }

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mTheEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
                    mTheEndView = viewStub.inflate();
                } else {
                    mTheEndView.setVisibility(VISIBLE);
                }

                mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                break;
            case NetWorkError:
                if (mJiaZaiView != null) {
                    mJiaZaiView.setVisibility(GONE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewstub);
                    mNetworkErrorView = viewStub.inflate();
                } else {
                    mNetworkErrorView.setVisibility(VISIBLE);
                }

                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                break;
            default:

                break;
        }
    }

    public static enum State {
        Normal/***/, TheEnd/***/, Loading/***/, NetWorkError/***/
    }
}