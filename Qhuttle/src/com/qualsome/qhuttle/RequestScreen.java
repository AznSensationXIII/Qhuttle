package com.qualsome.qhuttle; 

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.util.Log; 

public class RequestScreen extends Activity{
	EditText clientName, clientNumber; 
	Spinner numPassengers; 
	TimePicker pickupTime;  
	Spinner pickupLocation;
	TimePicker dropoffTime; 
	Spinner dropoffLocation; 
	RadioGroup asapNotice; 
	String nameString; 
	boolean asap; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.request_screen); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR); 
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Request a Shuttle"); 
		
		clientName = (EditText) findViewById(R.id.clientName); 
		clientNumber = (EditText) findViewByID(R.id.clientNumber);
		numPassengers = (Spinner) findViewById(R.id.numPassengers);
		pickupLocation = (Spinner) findViewById(R.id.pickupLocation);
	    dropoffLocation = (Spinner) findViewById(R.id.dropoffLocation);
	    
	    setUpDropdowns(); 
	    getNameString(); 
	    
		
	}
	
	public void getNameString(){
		nameString = clientName.getText().toString(); 
		Log.v("Edit Text", nameString); 
	}
	
	public void setUpDropdowns() {
		numPassengers = (Spinner) findViewById(R.id.numPassengers);
		pickupLocation = (Spinner) findViewById(R.id.pickupLocation);
		dropoffLocation = (Spinner) findViewById(R.id.dropoffLocation);

		numPassengers.setSelection(0); // set numPassengers to 1 
		
		Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
		numPassengers.setAdapter(adapter);
		
		String[] dropoffPlace = getResources().getStringArray(R.array.dropoffBuildingList); 
		ArrayAdapter<String> dropoffAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, dropoffPlace); 
		dropoffLocation.setAdapter(dropoffAdapter); 
		
		String[] pickUpPlace = getResources().getStringArray(R.array.BuildingList); 
		ArrayAdapter<String> pickUpAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, pickUpPlace); 
		pickupLocation.setAdapter(pickUpAdapter); 
		
		
	}

	
	
	public void onRadioButtonClicked(View view){
		boolean checked = ((RadioButton) view).isChecked(); 
		switch(view.getId()){
			case R.id.asapYes:
				if(checked)
					asap = true; 
				break; 
			case R.id.asapNo:
				if(checked)
					asap = false; 
				break; 
		}
		if (asap == true)
			Log.v("blah", "blahblah"); 
	}
	
	
	public void onLoginClick(View view)
	{
		AddPassengerData passengerData = new AddPassengerData();
		passengerData.name = clientName.getText().toString();
		passengerData.emp_number = Integer.parseInt(clientNumber.getText().toString());
		passengerData.num_pass = Integer.parseInt(numPassengers.getSelectedItem().toString());
		passengerData.pickup = pickupLocation.getSelectedItem().toString();
        passengerData.dropoff = dropoffLocation.getSelectedItem().toString();
        passengerData.hour = pickupTime.getCurrentHour();
        passengerData.min = pickupTime.getCurrentMinute();
        passengerData.ampm = ((passengerData.hour > 12) ? "PM" : "AM");
        if (passengerData.hour > 12) { passengerData.hour = passengerData.hour % 12; }
        passengerData.asap = asap;
        passengerData.driver_num = -1;
        passengerData.push_id = -1;
		new AddPassenger(RequestScreen.this).execute(passengerData);
	}
	
	
	private static class AddPassenger extends AsyncTask<AddPassengerData, Integer, Integer>
	{
		private static final int OK = 0;
		private static final int UNKNOWN = 1;
		private static final int NETWORK_ERROR = 2;
		
		private ProgressDialog progress;
		private Activity activity;
		private volatile boolean running;
		
		public AddPassenger(Activity act)
		{
			activity = act;
			running = true;
		}
		
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(activity, null, "Queueing up...");
			progress.setCanceledOnTouchOutside(true);
			progress.setOnCancelListener(new OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dialog)
				{
					cancel(true);
				}
			});
		}
		
		protected void onCancelled()
		{
			running = false;
		}
		
		@Override
		protected Integer doInBackground(AddPassengerData... params)
		{
			while (running)
			{
				try
				{
					HttpClient client = new DefaultHttpClient();
					String str = "http://54.213.39.1:8000/dispatcher/enqueue/";
					HttpPost httpPost = new HttpPost(str);
					
					String gson = new Gson().toJson(params[0], AddPassengerData.class);
					StringEntity se = new StringEntity(gson);
					
					httpPost.setEntity(se);
					httpPost.setHeader("Accept", "application/json");
			        httpPost.setHeader("Content-type", "application/json");
			        HttpResponse httpResponse = client.execute(httpPost);
					
					if (httpResponse.getStatusLine().getStatusCode() != 200)
						return UNKNOWN;
					return OK;
				}
				catch (Exception e)
				{
					Log.e("Error", e.toString());
					return NETWORK_ERROR;
				}
			}
			return NETWORK_ERROR;
		}
		
		protected void onPostExecute(Integer result)
		{
			progress.dismiss();
			
			if (result == OK)
			{
//				Intent intent = new Intent(activity, PassengerActivity.class);
//				activity.startActivity(intent);
//				activity.finish();
			}
		}
	}
}