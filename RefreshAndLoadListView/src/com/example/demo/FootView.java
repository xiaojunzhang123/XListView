package com.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FootView extends LinearLayout {
	private LinearLayout footView;
	private View footViewContent;

	private ProgressBar pb;
	private TextView tv_foot_state;

	public final static int NOMAL = 1;
	public final static int READY = 2;
	public final static int LOADING = 3;
	private Context mContext;

	public FootView(Context context) {
		super(context);
		initView(context);
	}

	public FootView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		mContext=context;
		footView = (LinearLayout) View.inflate(mContext, R.layout.footview,
				null);
		addView(footView);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp.gravity=Gravity.BOTTOM;
		footView.setLayoutParams(lp);
		
		footViewContent =footView
				.findViewById(R.id.xlistview_footer_content);
		pb = (ProgressBar) footView
				.findViewById(R.id.xlistview_footer_progressbar);
		tv_foot_state = (TextView) footView
				.findViewById(R.id.xlistview_footer_hint_textview);
	
		
	}

	public void setFootState(int state) {
		if(state==LOADING){
			pb.setVisibility(View.VISIBLE);
			tv_foot_state.setVisibility(View.INVISIBLE);
		}else{
			pb.setVisibility(View.INVISIBLE);
			tv_foot_state.setVisibility(View.VISIBLE);
		}
		switch (state) {
		case NOMAL:
			tv_foot_state.setText("上拉加载更多");
			break;
		case READY:
			tv_foot_state.setText("松开加载更多");
			break;
		case LOADING:

			break;

		default:
			break;
		}
	}
	
	public int getBottomMargin(){
		LinearLayout.LayoutParams layoutParams=(LayoutParams) footViewContent.getLayoutParams();
		return layoutParams.bottomMargin;
	}
	
	public void setBottomMargin(int margin){
		if(margin<0)
			return ;
		LinearLayout.LayoutParams layoutParams=(LayoutParams) footViewContent.getLayoutParams();
		layoutParams.bottomMargin=margin;
		footViewContent.setLayoutParams(layoutParams);
	}
	
	public void hideFoot(){
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
		footViewContent.setLayoutParams(layoutParams);
		
	}
	public void showFoot(){
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		footViewContent.setLayoutParams(layoutParams);
	}

}
