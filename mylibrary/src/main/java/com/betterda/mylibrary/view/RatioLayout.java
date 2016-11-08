package com.betterda.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.betterda.mylibrary.R;


public class RatioLayout extends FrameLayout {

	private float ratio = 0.0f;

	private boolean isLow = false;

	private Context mcontext;

	public RatioLayout(Context context) {
		this(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mcontext = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
		ratio = a.getFloat(R.styleable.RatioLayout_ratio, 0.0f);
		isLow = a.getBoolean(R.styleable.RatioLayout_isLow,false);
		a.recycle();
	}

	public void setRatio(float f) {
		ratio = f;
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
		if (widthMode == MeasureSpec.EXACTLY  && ratio != 0.0f) {

			height = (int) (width / ratio + 0.5f);

		/*	if (isLow) {
				if (mcontext != null) {

					if (height < 150) {
						height = UtilMethod.dip2px(mcontext, 150);
					}
				}

			}*/

			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(),
					MeasureSpec.EXACTLY);
		} else if ( heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
			width = (int) (height * ratio + 0.5f);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(),
					MeasureSpec.EXACTLY);
		} else {
			new RuntimeException("width or height must be query");
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
