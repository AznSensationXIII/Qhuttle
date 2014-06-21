package com.qualsome.qhuttledriver;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalListAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<PotentialListData.PotentialListItem> list;
	private int items;
	
	LocalListAdapter(Activity act, ArrayList<PotentialListData.PotentialListItem> potential)
	{
		activity = act;
		list = potential;
	}

	@Override
	public int getCount()
	{
		return items;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		PotentialListData.PotentialListItem data = list.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		view = inflater.inflate(R.layout.potential_list_item, null);
		
		TextView name = (TextView)view.findViewById(R.id.potential_passenger);
		name.setText(data.name + "(" + data.number + ")");
		
		TextView time = (TextView)view.findViewById(R.id.potential_time);
		time.setText(data.hour + ":" + data.minute + " " + data.ampm);
		
		TextView locations = (TextView)view.findViewById(R.id.potential_locations);
		locations.setText(data.pickup + " ---> " + data.dropoff);
		
		return view;
	}

}
