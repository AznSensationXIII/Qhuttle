package com.qualsome.qhuttledriver;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PassengerActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private static final int LOCAL = 0;
	private static final int POTENTIAL = 1;
	
	private LocalFragment localFrag;
	private PotentialFragment potentialFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passenger);
		// Show the Up button in the action bar.
		setupActionBar();
		
		localFrag = new LocalFragment();
		potentialFrag = new PotentialFragment();
		
		ActionBar action = getActionBar();
		action.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		action.addTab(action.newTab().setText("Local").setTabListener(this));
		action.addTab(action.newTab().setText("Potential").setTabListener(this));
		
		android.support.v4.app.FragmentTransaction trans =
			getSupportFragmentManager().beginTransaction();
		trans.add(R.id.passenger_container, localFrag, "local");
		trans.add(R.id.passenger_container, potentialFrag, "potential");
		trans.commit();
	}
	
	private void setupActionBar() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passenger, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		switch (tab.getPosition())
		{
		case LOCAL:
			trans.hide(potentialFrag);
			trans.show(localFrag);
			trans.commit();
			break;
		case POTENTIAL:
			trans.hide(localFrag);
			trans.show(potentialFrag);
			trans.commit();
			break;
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
