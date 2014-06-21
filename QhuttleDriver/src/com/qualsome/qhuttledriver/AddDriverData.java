package com.qualsome.qhuttledriver;

import com.google.gson.annotations.SerializedName;

public class AddDriverData
{
	@SerializedName("name")
	public String name;
	@SerializedName("employee_number")
	public int number;
	@SerializedName("latitude")
	public double latitude;
	@SerializedName("longitude")
	public double longitude;
}
