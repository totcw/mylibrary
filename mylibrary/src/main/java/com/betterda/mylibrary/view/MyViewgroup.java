package com.betterda.mylibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyViewgroup extends ViewGroup {

    private Context mContext;
    private int width; //radius
    private int i1;

    private int textSize = 50;


    private int normalColor = Color.RED; //nomal color
    private int normalTextColor =Color.BLACK;
    private int color = Color.GREEN; //pressed color
    private int textColor = Color.YELLOW;

    public MyViewgroup(Context context) {
        this(context, null);
    }

    public MyViewgroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();

    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            MyPoint myPoint = new MyPoint(mContext);

            final float scale = mContext.getResources().getDisplayMetrics().density;
            width = 50;
            i1 = (int) (width * scale + 0.5f);
            myPoint.setWidth(i1);
            myPoint.setmTitleText(i + "");
            myPoint.setTextSize(textSize);

            addView(myPoint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // jisuan  zi  view  de height and width
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int cCount = getChildCount();



        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            if (i < 4) {

                width += childView.getMeasuredWidth();
            }

            if (i == 0) {

                height += childView.getMeasuredHeight();
            } else if (i == 3) {
                height += childView.getMeasuredHeight();
            } else if (i == 6) {
                height += childView.getMeasuredHeight();
            } else if (i == 9) {
                height += childView.getMeasuredHeight();
            }

        }
        final float scale = mContext.getResources().getDisplayMetrics().density;
        int margin = (int) (10 * scale + 0.5f);
        width += margin * 2;
        height += margin * 3;

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int cCount = getChildCount();
        final float scale = mContext.getResources().getDisplayMetrics().density;
        int margin = (int) (10 * scale + 0.5f);


        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            int cWidth = 0;
            int cHeight = 0;
            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:

                    break;
                case 1:
                    cWidth = margin;
                    cl = childView.getMeasuredWidth();
                    break;
                case 2:
                    cl = childView.getMeasuredWidth() * 2;
                    cWidth = margin * 2;
                    break;
                case 3:
                    ct = childView.getMeasuredHeight();
                    cHeight = margin;
                    break;
                case 4:
                    cl = childView.getMeasuredWidth();
                    ct = childView.getMeasuredHeight();
                    cWidth = margin;
                    cHeight = margin;
                    break;
                case 5:
                    cl = childView.getMeasuredWidth() * 2;
                    ct = childView.getMeasuredHeight();
                    cWidth = margin * 2;
                    cHeight = margin;
                    break;
                case 6:
                    ct = childView.getMeasuredHeight() * 2;
                    cHeight = margin * 2;
                    break;
                case 7:
                    cWidth = margin;
                    cHeight = margin * 2;
                    cl = childView.getMeasuredWidth();
                    ct = childView.getMeasuredHeight() * 2;
                    break;
                case 8:
                    cl = childView.getMeasuredWidth() * 2;
                    ct = childView.getMeasuredHeight() * 2;
                    cWidth = margin * 2;
                    cHeight = margin * 2;
                    break;
                case 9:
                    cl = childView.getMeasuredWidth();
                    ct = childView.getMeasuredHeight() * 3;
                    cWidth = margin;
                    cHeight = margin * 3;
                    break;

            }
            cl = cl + cWidth;
            ct = ct + cHeight;
            cr = cl + childView.getMeasuredWidth();
            cb = ct + childView.getMeasuredHeight();

            childView.layout(cl, ct, cr, cb);
        }

    }

    /**
     * setting radius
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * setting text size
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * setting click
     * @param listener
     */
    public void setFirstClick(OnClickListener listener) {
        View childView = getChildAt(1);
        childView.setOnClickListener(listener);
    }

    public void setTwoClick(OnClickListener listener) {
        View childView = getChildAt(2);
        childView.setOnClickListener(listener);
    }

    public void setThreeClick(OnClickListener listener) {
        View childView = getChildAt(3);
        childView.setOnClickListener(listener);
    }

    public void setFourClick(OnClickListener listener) {
        View childView = getChildAt(4);
        childView.setOnClickListener(listener);
    }

    public void setFiveClick(OnClickListener listener) {
        View childView = getChildAt(5);
        childView.setOnClickListener(listener);
    }

    public void setSixClick(OnClickListener listener) {
        View childView = getChildAt(6);
        childView.setOnClickListener(listener);
    }

    public void setSevenClick(OnClickListener listener) {
        View childView = getChildAt(7);
        childView.setOnClickListener(listener);
    }

    public void setEightClick(OnClickListener listener) {
        View childView = getChildAt(8);
        childView.setOnClickListener(listener);
    }

    public void setNineClick(OnClickListener listener) {
        View childView = getChildAt(9);
        childView.setOnClickListener(listener);
    }

    public void setZeroClick(OnClickListener listener) {
        View childView = getChildAt(0);
        childView.setOnClickListener(listener);
    }

    /**
     * setting normal color
     *
     * @param normalColor     background color
     * @param normaltextColor text color
     */
    public void setNormalColor(int normalColor, int normaltextColor) {
        this.normalColor = normalColor;
        this.normalTextColor = normaltextColor;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            MyPoint childView = (MyPoint) getChildAt(i);
            childView.setNormalColor(normalColor, normaltextColor);
           // childView.postInvalidate();
        }
    }

    /**
     * setting pressed color
     *
     * @param Color     background color
     * @param textColor  text color
     */
    public void setPressedColor(int Color, int textColor) {
        this.color = Color;
        this.textColor = textSize;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            MyPoint childView = (MyPoint) getChildAt(i);
            childView.setNormalColor(color,textColor);
            //childView.postInvalidate();
        }
    }


}
