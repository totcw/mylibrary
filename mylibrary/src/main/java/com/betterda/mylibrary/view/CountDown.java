package com.betterda.mylibrary.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.R;


/**
 * Created by lyf
 *
 */
public class CountDown extends FrameLayout {
    private TextView tv_yzm;
    private  TextView tv_countdown;
    private RelativeLayout  mRelativeCountdown;
    private Handler handler = new Handler();
    private int time = 60;
    private onSelectListener listener;
    public CountDown(Context context) {
        this(context,null);
    }

    public CountDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public CountDown(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.countdown_view,this);
        tv_yzm = (TextView) findViewById(R.id.tv_countdown_yzm);
        tv_countdown = (TextView) findViewById(R.id.tv_countdown);
        mRelativeCountdown = (RelativeLayout) findViewById(R.id.relative_countdown);
    }


    public void showCountDown(final String time2, final String time3){
        tv_yzm.setVisibility(View.INVISIBLE);
        tv_countdown.setVisibility(View.VISIBLE);
        setClickable(false);
        setSelected(false);
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    time--;
                    if (time > 0) {
                        tv_countdown.setText(time+time2);
                        handler.postDelayed(this, 1000);
                    }else{
                        time = 60;
                        tv_countdown.setText(time3);
                        tv_yzm.setVisibility(View.VISIBLE);
                        tv_countdown.setVisibility(View.INVISIBLE);
                        setClickable(true);
                        if (listener != null) {
                            listener.setSelect(CountDown.this);
                        }
                    }
                }
            },1000);
        }



    }

    public void ondestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }

    public interface onSelectListener {
        void setSelect(CountDown countDown);
    }

    public void setCountdownBackground(int resid) {
        mRelativeCountdown.setBackgroundResource(resid);
    }

}
