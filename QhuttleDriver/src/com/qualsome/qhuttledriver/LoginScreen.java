package com.qualsome.qhuttledriver;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class LoginScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login_screen,
					container, false);
			return rootView;
		}
	}
	
	public void onLoginClick(View view)
	{
		AddDriverData driverData = new AddDriverData();
		driverData.name = "name";
		driverData.number = 1;
		driverData.latitude = 100.0;
		driverData.longitude = 100.0;
		new Login(LoginScreen.this).execute(driverData);
	}
	
	private static class Login extends AsyncTask<AddDriverData, Integer, Integer>
	{
		private static final int OK = 0;
		private static final int UNKNOWN = 1;
		private static final int NETWORK_ERROR = 2;
		
		private ProgressDialog progress;
		private Activity activity;
		private volatile boolean running;
		
		public Login(Activity act)
		{
			activity = act;
			running = true;
		}
		
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(activity, null, "Logging In...");
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
		protected Integer doInBackground(AddDriverData... params)
		{
			while (running)
			{
				try
				{
					HttpClient client = new DefaultHttpClient();
					String str = "http://54.213.39.1:8000/dispatcher/add_driver/";
					HttpPost httpPost = new HttpPost(str);
					
					String gson = new Gson().toJson(params[0], AddDriverData.class);
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
				Intent intent = new Intent(activity, PassengerActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		}
	}

}
