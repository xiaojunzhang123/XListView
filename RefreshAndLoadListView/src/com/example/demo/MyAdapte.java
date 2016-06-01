package com.example.demo;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapte extends BaseAdapter {
	private List<String> data;
	private Context context;
	
	
	

	public MyAdapte(List<String> data, Context context) {
		super();
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold holder;
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=View.inflate(context, R.layout.listview_item, null);
			holder=new ViewHold();
			holder.tv=(TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder=(ViewHold) convertView.getTag();
		}
		String str=data.get(position);
		holder.tv.setText(str);
		
		return convertView;
	}
	
	static class ViewHold{
		TextView tv;
	}

}
