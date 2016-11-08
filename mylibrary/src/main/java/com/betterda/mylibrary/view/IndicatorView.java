package com.betterda.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.mylibrary.R;


/**
 *
 *
 * @author Administrator
 */
public class IndicatorView extends LinearLayout {

    private ImageView iv_bottom;
    private TextView tv_bottom;
    private int normalColor;
    private int selectColor;
    private int normaliv;
    private int selectiv;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.bottom_layout, this);
      //  bottom_line = findViewById(R.id.bottom_line);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
    }

    /**
     *
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_bottom.setText(title);
    }

    /**
     *
     *
     * @param normaliv
     * @param selectiv
     */
    public void setIvBackground(int normaliv, int selectiv) {
        this.normaliv = normaliv;
        this.selectiv = selectiv;
        //
        iv_bottom.setBackgroundResource(normaliv);

    }

    /**
     *
     *
     * @param normalColor
     * @param selectColor
     */
    public void setLineBackground(int normalColor, int selectColor) {
        this.normalColor = normalColor;
        this.selectColor = selectColor;
        //
     //   bottom_line.setBackgroundColor(normalColor);
        tv_bottom.setTextColor(normalColor);

    }

    /**
     *
     *
     * @param selected
     */
    public void setTabSelected(boolean selected) {
        if (selected) {
          //  bottom_line.setBackgroundColor(selectColor);
          tv_bottom.setTextColor(selectColor);
            iv_bottom.setBackgroundResource(selectiv);
        } else {
          //  bottom_line.setBackgroundColor(normalColor);
            tv_bottom.setTextColor(normalColor);
            iv_bottom.setBackgroundResource(normaliv);
        }
    }


}
