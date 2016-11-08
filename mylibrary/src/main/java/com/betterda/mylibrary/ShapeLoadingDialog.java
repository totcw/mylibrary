package com.betterda.mylibrary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 *
 * Created by zzz40500 on 15/6/15.
 */
public class ShapeLoadingDialog {



    private Context mContext;
    private Dialog mDialog;
  //  private LoadingView mLoadingView;
    private View mDialogContentView;
    private TextView textView;
    private ImageView iv;
    AnimationDrawable anim;
    public ShapeLoadingDialog(Context context) {
        this.mContext=context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog);
        mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.loading, null);
        iv = (ImageView) mDialogContentView.findViewById(R.id.iv_loading_progressbar);
        iv.setBackgroundResource(R.drawable.progressbar);
        anim = (AnimationDrawable) iv.getBackground();

        mDialog.setCancelable(false);
        textView= (TextView) mDialogContentView.findViewById(R.id.tv_jiazai);
        mDialog.setContentView(mDialogContentView);
    }

    public void setBackground(int color){
        GradientDrawable gradientDrawable= (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence){
        textView.setText(charSequence);
    }

    public void show(){
        mDialog.show();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        iv.post(new Runnable() {
            @Override
            public void run() {
                anim.start();

            }
        });
    }

    public void dismiss(){
        mDialog.dismiss();
        if (anim != null) {
            anim.stop();
        }
    }

    public Dialog getDialog(){
        return  mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
    }
}
