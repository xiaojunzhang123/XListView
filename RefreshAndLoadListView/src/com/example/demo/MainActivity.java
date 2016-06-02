package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.demo.XListView.PullAndLoadListener;

public class MainActivity extends Activity {
	private XListView lv;
	private MyAdapte adapte;
	private List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (XListView) findViewById(R.id.lv);
		initData();
        lv.enableLoad(true);
        lv.enableRefresh(true);
		lv.setPullAndLoadListener(new PullAndLoadListener() {

			@Override
			public void refresh() {
				// TODO Auto-generated method stub
                handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						lv.stopRefresh();
					}
				}, 3000);

			}

			@Override
			public void load() {
				// TODO Auto-generated method stub
				lv.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						lv.stopLoad();

					}
				}, 3000);

			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("this is" + i);
		}
		adapte = new MyAdapte(list, this);
		lv.setAdapter(adapte);

	}
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			

		}
		
		
	};

}
