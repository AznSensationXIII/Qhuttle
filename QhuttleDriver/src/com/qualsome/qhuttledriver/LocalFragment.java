package com.qualsome.qhuttledriver;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LocalFragment extends Fragment {
	
	private ArrayList<PotentialListData.PotentialListItem> localList;
	ListView listView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_local,
				container, false);
		
		//listView = (ListView)view.findViewById(R.id.potential_list_scheduled);
		//listView.setAdapter(new LocalListAdapter(getActivity(), localList));
		
		return view;
	}

}
