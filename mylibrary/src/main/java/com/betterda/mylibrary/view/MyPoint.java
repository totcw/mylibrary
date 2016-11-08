package com.betterda.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyPoint extends View {
    private Paint mPaint, mPaint2;//
    private float widthP;
    private Context mcontext;
    /**
     *
     * @param mTitleText
     */
    public void setmTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
    }

    /**
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    private int textSize = 50;

    private int normalColor = Color.RED;
    private int normalTextColor =Color.BLACK;
    private int color = Color.GREEN;
    private int textColor = Color.YELLOW;
    /**
     *
     * @param normalColor
     * @param normaltextColor
     */
    public void setNormalColor(int normalColor, int normaltextColor) {
        this.normalColor = normalColor;
        this.normalTextColor = normaltextColor;
        mPaint.setColor(normalColor);
        mPaint2.setColor(normalTextColor);
        postInvalidate();
    }

    /**
     */
    public void setPressedColor(int Color, int textColor) {
        this.color = Color;
        this.textColor = textSize;
        mPaint.setColor(color);
        mPaint2.setColor(textColor);
        postInvalidate();
    }


    /**
     */
    private Rect mBound;
    private String mTitleText="1";

    public void setWidth(float widthP) {
        this.widthP = widthP;
    }

    public MyPoint(Context context) {
        this(context, null);
    }

    public MyPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        init();
    }


    public MyPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null);
    }

    private void init() {


        mPaint = new Paint();

        mPaint2 = new Paint();
        mPaint2.setColor(normalTextColor);
        mPaint2.setTextSize(50);
        mBound = new Rect();
        mPaint2.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        final float scale = mcontext.getResources().getDisplayMetrics().density;
        widthP= (int) (50 * scale + 0.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {

            float textWidth = widthP * 2;
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {

            float textHeight = widthP * 2;
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(widthP, widthP, widthP, mPaint);
        canvas.drawText(mTitleText, widthP - mBound.width() , widthP + mBound.height()/2, mPaint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                setCo();
                break;
            case MotionEvent.ACTION_DOWN:
                setCo();
                break;
            case MotionEvent.ACTION_UP:
                setCo2();
                break;
            case MotionEvent.ACTION_CANCEL:
                setCo2();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setCo() {
        if (mPaint != null&&mPaint2!=null) {

        mPaint.setColor(color);
        mPaint2.setColor(textColor);
        postInvalidate();
        }
    }
    public void setCo2() {
        if (mPaint != null&&mPaint2!=null) {
            mPaint.setColor(normalColor);
            mPaint2.setColor(normalTextColor);
            postInvalidate();
        }
    }

}
