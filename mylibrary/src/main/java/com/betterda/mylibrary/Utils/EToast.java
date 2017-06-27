package com.betterda.mylibrary.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.mylibrary.R;

/**
 * Created by Administrator on 2016/11/21.
 */

public class EToast {

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    private static EToast result;
    //anim time
    private final int ANIMATION_DURATION = 600;
    private static TextView mTextView;
    private ViewGroup container;
    private View v;
    //show time
    private int HIDE_DELAY = 2000;
    private LinearLayout mContainer;
    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;
    private boolean isShow = false;
    private static Context mContext;
    private Handler mHandler = new Handler();

    private EToast(Context context) {
        mContext = context;
        container = (ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content);
        View viewWithTag = container.findViewWithTag(context.getClass().getName());
        if(viewWithTag == null){
            v = ((Activity) context).getLayoutInflater().inflate(
                    R.layout.etoast, container);
            v.setTag(context.getClass().getName());
        }else{
            v = viewWithTag;
        }
        mContainer = (LinearLayout) v.findViewById(R.id.mbContainer);
        mContainer.setVisibility(View.GONE);
        mTextView = (TextView) v.findViewById(R.id.mbMessage);
    }

    public static EToast makeText(Context context, String message, int HIDE_DELAY) {
        if(result == null){
            result = new EToast(context);
        }else{
            // update context when change activity
            if(!mContext.getClass().getName().equals(context.getClass().getName())){
                result = new EToast(context);
            }
        }
        if(HIDE_DELAY == LENGTH_LONG){
            result.HIDE_DELAY = 2500;
        }else{
            result.HIDE_DELAY = 1500;
        }
        mTextView.setText(message);
        return result;
    };
    public static EToast makeText(Context context, int resId, int HIDE_DELAY) {
        String mes = "";
        try{
            mes = context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return makeText(context,mes,HIDE_DELAY);
    }
    public void show() {
        if(isShow){

            return;
        }
        isShow = true;
        // show anim
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        //dismiss anim
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        isShow = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        mContainer.setVisibility(View.GONE);
                        reset();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
        mContainer.setVisibility(View.VISIBLE);

        mFadeInAnimation.setDuration(ANIMATION_DURATION);

        mContainer.startAnimation(mFadeInAnimation);
        mHandler.postDelayed(mHideRunnable, HIDE_DELAY);
    }

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(mFadeOutAnimation);
        }
    };
    public void cancel(){
        if(isShow) {
            isShow = false;
            mContainer.setVisibility(View.GONE);
            mHandler.removeCallbacks(mHideRunnable);
        }
    }

    //You should call in baseactivity's ondestroy
    public static void reset(){
        if (result != null) {
            result.remove();
            result.v = null;
            result.mTextView = null;
            result.mContainer = null;
            result.container = null;
            result.mFadeInAnimation = null;
            result.mFadeOutAnimation = null;
            result.mHandler = null;
            result.mContext = null;
            result = null;
        }
    }

    /**
     * remove last toast when change activity
     */
    public void remove() {
        if (container != null&&mContainer!=null) {
            container.removeView(mContainer);
        }

    }


    public void setText(CharSequence s){
        if(result == null) return;
        TextView mTextView = (TextView) v.findViewById(R.id.mbMessage);
        if(mTextView == null) throw new RuntimeException("This Toast was not created with Toast.makeText()");
        mTextView.setText(s);
    }
    public void setText(int resId) {
        setText(mContext.getText(resId));
    }

}
