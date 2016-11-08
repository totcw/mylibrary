package com.betterda.mylibrary.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.betterda.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class Gradient extends RelativeLayout {

    private List<ImageView> imageViews;
    private List<Animation> outAnim;
    private List<Animation> inAnim;
    private Context mContext;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int couot;
    private int currentIndex;
    private LinearLayout linearLayout;
    private onClickListner listner;
    private long time=3000;


    public Gradient(Context context) {
        this(context, null);
    }

    public Gradient(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    public void cratePoint() {
        if (null != imageViews && imageViews.size() > 0) {
            int size = imageViews.size();

            linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);

            for (int i = 0; i < size; i++) {

                View viewPoint = new View(mContext);
                viewPoint.setBackgroundResource(R.drawable.point_background);
                int weight = dip2px(mContext, 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(weight, weight);
                lp.leftMargin = weight;
                viewPoint.setLayoutParams(lp);
                viewPoint.setEnabled(false);
                linearLayout.addView(viewPoint);
            }
            View childAt = linearLayout.getChildAt(0);
            if (null != childAt) {
                childAt.setEnabled(true);
            }

            LayoutParams rlp = new LayoutParams(-1,-2);
            rlp.bottomMargin = dip2px(mContext, 5);
            rlp.addRule(ALIGN_PARENT_BOTTOM);
            this.addView(linearLayout, rlp);

        }


    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
        for (int i = 0; i < imageViews.size(); i++) {
            LayoutParams layoutParams = new LayoutParams(-1,-1);
            addView(imageViews.get(i),layoutParams);
        }
        setonClick();
        cratePoint();
        createAnim();
        start();

    }

    private void start() {
        final int size = imageViews.size();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final int i = couot % size;

                for (int j = 0; j < size; j++) {


                    if (j == i) {
                        imageViews.get(i).setClickable(true);


                    } else {
                        imageViews.get(i).setClickable(false);
                    }
                }

                if (couot < size) {
                    if (i == size - 1) {


                        ImageView imageView = imageViews.get(0);
                        imageView.startAnimation(outAnim.get(0));

                        ImageView imageView2 = imageViews.get(size - 1);
                        imageView2.startAnimation(inAnim.get(size - 1));


                    } else {

                        ImageView imageView = imageViews.get(size - 1 - i);
                        imageView.startAnimation(outAnim.get(size - 1 - i));


                    }
                } else {

                    if (i == size - 1) {

                        ImageView imageView = imageViews.get(0);
                        imageView.startAnimation(outAnim.get(0));

                        ImageView imageView2 = imageViews.get(size - 1);
                        imageView2.startAnimation(inAnim.get(size - 1));


                    } else {

                        ImageView imageView = imageViews.get(size - 1 - i);
                        imageView.startAnimation(outAnim.get(size - 1 - i));

                        ImageView imageView2 = imageViews.get(size - 2 - i);
                        imageView2.startAnimation(inAnim.get(size - 2 - i));

                    }
                }
                currentIndex = i;
                linearLayout.getChildAt(currentIndex % size).setEnabled(false);
                currentIndex++;
                linearLayout.getChildAt(currentIndex % size).setEnabled(true);
                couot++;
                handler.postDelayed(this, time);
            }
        });
    }


    private void createAnim() {
        outAnim = new ArrayList<>();
        inAnim = new ArrayList<>();
        for (int i = 0; i < imageViews.size(); i++) {
            Animation zoomOutAwayAnim = createZoomOutAwayAnim();
            zoomOutAwayAnim.setFillAfter(true);
            outAnim.add(zoomOutAwayAnim);

            Animation zoomOutNearAnim = createZoomOutNearAnim();
            zoomOutNearAnim.setFillAfter(true);
            inAnim.add(zoomOutNearAnim);

        }
    }


    public void setonClick() {
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        listner.setonClick((currentIndex) % imageViews.size());
                    }

                }
            });
        }
    }

    public interface onClickListner{

        void setonClick(int position);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setListner(onClickListner listner) {
        this.listner = listner;
    }

    public  Animation createZoomOutAwayAnim() {
        AnimationSet ret;
        Animation anim;
        ret = new AnimationSet(false);

        anim = new AlphaAnimation(1f, 0f);
        anim.setDuration(time);
        anim.setInterpolator(new DecelerateInterpolator());
        ret.addAnimation(anim);

        return ret;
    }

    public  Animation createZoomOutNearAnim() {
        AnimationSet ret;
        Animation anim;
        ret = new AnimationSet(false);

        anim = new AlphaAnimation(0f, 1f);
        anim.setDuration(time);
        anim.setInterpolator(new LinearInterpolator());
        ret.addAnimation(anim);

        return ret;
    }

    public void stop() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            if (imageViews != null) {
                imageViews.clear();
                imageViews = null;
            }
            if (outAnim != null) {
                outAnim.clear();
                outAnim = null;
            }
            if (inAnim != null) {
                inAnim.clear();
                inAnim = null;
            }
        }
    }
}
