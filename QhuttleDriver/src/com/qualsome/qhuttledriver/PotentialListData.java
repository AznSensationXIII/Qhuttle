package com.qualsome.qhuttledriver;

import com.google.gson.annotations.SerializedName;

public class PotentialListData
{
	@SerializedName("asap")
	public PotentialListItem[] asap;
	@SerializedName("scheduled")
	public PotentialListItem[] scheduled;
	
	public static class PotentialListItem
	{
		@SerializedName("name")
		public String name;
		@SerializedName("employee_number")
		public int number;
		@SerializedName("num_passengers")
		public int passengers;
		@SerializedName("location_pickup")
		public String pickup;
		@SerializedName("location_dropoff")
		public String dropoff;
		@SerializedName("request_hour")
		public int hour;
		@SerializedName("request_min")
		public int minute;
		@SerializedName("request_AMPM")
		public String ampm;
		@SerializedName("asap_flag")
		public boolean asap;
	}
}
