package com.betterda.mylibrary.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.betterda.mylibrary.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class LoadingView2 extends FrameLayout {
    private ImageView imageView;
    private AnimationDrawable mAnimation;
    public LoadingView2(Context context) {
        this(context, null);
    }

    public LoadingView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.loading_run, this);
        imageView = (ImageView) findViewById(R.id.iv_loading_run);
        imageView.setBackgroundResource(R.drawable.loadinganim);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) imageView.getBackground();

    }

    public void startAnim() {
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        imageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();

            }
        });
    }


    public void stopAnim() {
        if (mAnimation != null) {
           mAnimation.stop();
        }
    }
}
