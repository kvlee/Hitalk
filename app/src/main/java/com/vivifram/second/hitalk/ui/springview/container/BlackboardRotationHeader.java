package com.vivifram.second.hitalk.ui.springview.container;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.ui.view.CircleProgressBar;

/**
 * Created by zuowei on 16-8-11.
 */
public class BlackboardRotationHeader extends BaseHeader {
    private Context context;
    private int rotationSrc;
    private int arrowSrc;
    private int logoSrc;
    private boolean isShowText;

    private final int ROTATE_ANIM_DURATION = 180;
    private RotateAnimation mRotateUpAnim;
    private RotateAnimation mRotateDownAnim;

    private TextView headerTitle;
    private ImageView headerArrow;
    private ImageView headerLogo;
    private CircleProgressBar headerProgressbar;
    private View frame;

    public BlackboardRotationHeader(Context context){
        this(context, 0,R.drawable.arrow,0,false);
    }

    public BlackboardRotationHeader(Context context,boolean isShowText){
        this(context, 0,R.drawable.arrow,0,isShowText);
    }

    public BlackboardRotationHeader(Context context,int logoSrc){
        this(context, 0,R.drawable.arrow,logoSrc,false);
    }

    public BlackboardRotationHeader(Context context,int logoSrc,boolean isShowText){
        this(context, 0, R.drawable.arrow,logoSrc,isShowText);
    }

    public BlackboardRotationHeader(Context context,int rotationSrc,int arrowSrc,int logoSrc,boolean isShowText){
        this.context = context;
        this.rotationSrc = rotationSrc;
        this.arrowSrc = arrowSrc;
        this.logoSrc = logoSrc;
        this.isShowText = isShowText;
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    @Override
    public View getView(LayoutInflater inflater,ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.blackboard_header, viewGroup, true);
        headerTitle = (TextView) view.findViewById(R.id.bb_header_text);
        headerArrow = (ImageView) view.findViewById(R.id.bb_header_arrow);
        headerProgressbar = (CircleProgressBar) view.findViewById(R.id.bb_header_progressbar);
        frame = view.findViewById(R.id.bb_frame);
        if(!isShowText) headerTitle.setVisibility(View.GONE);
        headerArrow.setImageResource(arrowSrc);
        return view;
    }

    @Override
    public int getDragSpringHeight(View rootView) {
        return frame.getMeasuredHeight();
    }

    @Override
    public int getDragLimitHeight(View rootView) {
        return frame.getMeasuredHeight();
    }

    @Override
    public void onPreDrag(View rootView) {
    }

    @Override
    public void onDropAnim(View rootView, int dy) {
    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (!upORdown){
            headerTitle.setText("松开刷新");
            if (headerArrow.getVisibility()==View.VISIBLE)
                headerArrow.startAnimation(mRotateUpAnim);
        }
        else {
            headerTitle.setText("下拉刷新");
            if (headerArrow.getVisibility()==View.VISIBLE)
                headerArrow.startAnimation(mRotateDownAnim);
        }
    }

    @Override
    public void onStartAnim() {
        headerTitle.setText("正在刷新");
        headerArrow.setVisibility(View.INVISIBLE);
        headerArrow.clearAnimation();
        headerProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishAnim() {
        headerTitle.setText("下拉刷新");
        headerArrow.setVisibility(View.VISIBLE);
        headerProgressbar.setVisibility(View.INVISIBLE);
    }
}