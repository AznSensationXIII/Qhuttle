package com.qualsome.qhuttle;

import com.google.gson.annotations.SerializedName;
 
public class AddPassengerData
{
        @SerializedName("name")
        public String name;
        @SerializedName("employee_number")
        public int emp_number;
        @SerializedName("num_passengers")
        public int num_pass;
        @SerializedName("location_pickup")
        public String pickup;
        @SerializedName("location_dropoff")
        public String dropoff;
        @SerializedName("request_hour")
        public int hour;
        @SerializedName("request_minute")
        public int min;
        @SerializedName("request_AMPM")
        public String ampm;
        @SerializedName("asap_flag")
        public boolean asap;
        @SerializedName("driver_number")
        public int driver_num;
        @SerializedName("push_id")
        public int push_id;
 
}