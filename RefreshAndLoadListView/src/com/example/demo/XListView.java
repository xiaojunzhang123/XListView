package com.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class XListView extends ListView implements OnScrollListener {
	private HeadView headView;
	private FootView footView;
	
	private Scroller msScroller;
	private final static int SCROLL_DURATION = 400;

	private final static float OFFSET_RADIO = 1.8f;
	private int headViewHeight = 0;
    private boolean ISREFRESHING=false;
    private LinearLayout ll_head_content;
	private int lastY = -1;

	private PullAndLoadListener pullAndLoadListener;

	private int totalNumView = -1;
	private boolean ISLOADING=false;
	private int footViewHeight=0;
	private RelativeLayout xlistview_footer_content;
	
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;
	
	private boolean enableRefresh=false;
	private boolean enableLoad=false;

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		initHeadView();
		initFootView();
	}

	private void initFootView() {
		footView=new FootView(getContext());
		xlistview_footer_content=(RelativeLayout) footView.findViewById(R.id.xlistview_footer_content);
		xlistview_footer_content.measure(0, 0);
		footViewHeight=xlistview_footer_content.getMeasuredHeight();
		addFooterView(footView);
	}

	private void initHeadView() {
		// TODO Auto-generated method stub
		msScroller=new Scroller(getContext(), new DecelerateInterpolator());
		super.setOnScrollListener(this);
		headView = new HeadView(getContext());
		ll_head_content=(LinearLayout) headView.findViewById(R.id.ll_head_content);
		addHeaderView(headView);
		ll_head_content.measure(0, 0);
		headViewHeight=ll_head_content.getMeasuredHeight();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (lastY == -1) {
			lastY = (int) ev.getRawY();
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = (int) ev.getRawY();

			break;
		case MotionEvent.ACTION_MOVE:
			int currentY = (int) (ev.getRawY() - lastY);
			lastY = (int) ev.getRawY();
			if ((currentY >= 0 || headView.getVisiableHeight() > 0)
					&& getFirstVisiblePosition() == 0&&enableRefresh) {
				updateHeadHeight(currentY / OFFSET_RADIO);
				
			} else if ((getLastVisiblePosition() == totalNumView - 1)
					&& currentY < 0&&enableLoad) {
				updateFootHeight(-(currentY / OFFSET_RADIO));

			}

			break;

		case MotionEvent.ACTION_UP:
			lastY = -1;
			if (getFirstVisiblePosition() == 0) {
				if (headView.getVisiableHeight() > headViewHeight) {
					ISREFRESHING=true;
					headView.setHeadStatu(HeadView.REFRESHING);
					pullAndLoadListener.refresh();
				}
				resetHeadHeight();
			}
			if(getLastVisiblePosition()==totalNumView-1){
				if(footView.getBottomMargin()>footViewHeight){
					ISLOADING=true;
					footView.setFootState(FootView.LOADING);
					pullAndLoadListener.load();
				}
				resetFootHeight();
			}

			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	private void resetFootHeight() {
		// TODO Auto-generated method stub
		int margin=footView.getBottomMargin();
		if(margin>0){
			mScrollBack=SCROLLBACK_FOOTER;
			msScroller.startScroll(0, margin, 0, -margin, SCROLL_DURATION);
			invalidate();
		}
	}

	private void updateFootHeight(float margin) {
		// TODO Auto-generated method stub
		footView.setBottomMargin((int)(margin+footView.getBottomMargin()));
		if(!ISLOADING){
			if(footView.getBottomMargin()>footViewHeight){
				footView.setFootState(FootView.READY);
			}else{
				footView.setFootState(FootView.NOMAL);
			}
		}
		setSelection(totalNumView-1);
	}

	private void updateHeadHeight(float delta) {
		headView.setVisiableHeight((int) (delta+headView.getVisiableHeight()));
		if(!ISREFRESHING){
			if(headView.getVisiableHeight()>headViewHeight){
				headView.setHeadStatu(HeadView.READY);
			}else{
				headView.setHeadStatu(HeadView.NORMAL);
			}
		}
		setSelection(0);
		
	}

	private void resetHeadHeight() {
		int height = headView.getVisiableHeight();
		if (height == 0)
			return;
		if (ISREFRESHING&&height <= headViewHeight) {
			return;
		}
		int tempHeight=0;
		if(ISREFRESHING&&height > headViewHeight){
			tempHeight=headViewHeight;
		}
		mScrollBack=SCROLLBACK_HEADER;
		msScroller.startScroll(0, height, 0, tempHeight-height, SCROLL_DURATION);
		invalidate();
		

	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(msScroller.computeScrollOffset()){
			if(mScrollBack==SCROLLBACK_HEADER){
				headView.setVisiableHeight(msScroller.getCurrY());
			}else{
				footView.setBottomMargin(msScroller.getCurrY());
			}
			
			postInvalidate();
		}
		super.computeScroll();
	}

	public interface PullAndLoadListener {
		void refresh();

		void load();
	}

	public void setPullAndLoadListener(PullAndLoadListener pullAndLoadListener) {
		this.pullAndLoadListener = pullAndLoadListener;
	}

	public void stopLoad() {
		if(ISLOADING==true){
			ISLOADING=false;
			resetFootHeight();
			footView.setFootState(FootView.NOMAL);
		}

	}

	public void stopRefresh() {
		if(ISREFRESHING==true){
			ISREFRESHING=false;
			resetHeadHeight();
			headView.formatTime(System.currentTimeMillis());
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		totalNumView = totalItemCount;
		

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
	
	public void enableRefresh(boolean refresh){
		
		enableRefresh=refresh;
	}
	
	public void enableLoad(boolean isload){
		enableLoad=isload;
		if(isload){
			footView.showFoot();
		}else{
			footView.hideFoot();
		}
		
	}
}
