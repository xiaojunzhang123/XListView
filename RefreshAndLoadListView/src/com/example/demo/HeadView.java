package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeadView extends LinearLayout {
	private LinearLayout headView;

	private ProgressBar pb_head;
	private ImageView iv_head;
	private TextView tv_head_state;
	private TextView tv_head_time;
	private int headHeight;

	private Animation upAnimation;
	private Animation downAnimation;

	public final static int NORMAL = 1;
	public final static int READY = 2;
	public final static int REFRESHING = 3;

	private int mStatu = NORMAL;

	public HeadView(Context context) {
		super(context);
		initView();
		initAnimation();
	}

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initAnimation();
	}

	private void initView() {
		// TODO Auto-generated method stub
		headView = (LinearLayout) View.inflate(getContext(), R.layout.headview,
				null);
		pb_head = (ProgressBar) headView.findViewById(R.id.pb_head);
		iv_head = (ImageView) headView.findViewById(R.id.iv_head);
		tv_head_state = (TextView) headView.findViewById(R.id.tv_head_state);
		tv_head_time = (TextView) headView.findViewById(R.id.tv_head_time);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		addView(headView, layoutParams);
		setGravity(Gravity.BOTTOM);
	}

	private void initAnimation() {
		upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);

	}

	public void formatTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String refreshTime = sdf.format(date);
		tv_head_time.setText(refreshTime);
		
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;

		LinearLayout.LayoutParams lp = (LayoutParams) headView
				.getLayoutParams();
		lp.height = height;
		headView.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return headView.getHeight();
	}

	public void setHeadStatu(int state) {
		if (state == mStatu)
			return;
		if (state == REFRESHING) {
			iv_head.setVisibility(View.INVISIBLE);
			pb_head.setVisibility(View.VISIBLE);
			iv_head.clearAnimation();
		} else {
			iv_head.setVisibility(View.VISIBLE);
			pb_head.setVisibility(View.INVISIBLE);

		}

		switch (state) {
		case NORMAL:
			if(mStatu==READY){
				iv_head.startAnimation(downAnimation);
			}
			if(mStatu==REFRESHING){
				iv_head.clearAnimation();
			}
			tv_head_state.setText("下拉刷新");
			break;
		case READY:
			if(mStatu!=READY){
				iv_head.clearAnimation();
				iv_head.startAnimation(upAnimation);
				tv_head_state.setText("松开刷新");
			}

			break;
		case REFRESHING:
			tv_head_state.setText("正在刷新");
			break;

		default:
			break;
		}
		mStatu=state;

	}
}
