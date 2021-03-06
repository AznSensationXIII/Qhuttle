from django.db import models

# Create your models here.
class Driver(models.Model): 
    name = models.CharField(max_length=30)
    employnum = models.IntegerField()
    # picture = models.ImageField()
    lati = models.FloatField()
    longi = models.FloatField()

    def to_dict(self):
        return { 'name' : self.name,
                 'latitude' : self.lati,
                 'longitude' : self.longi }

class Passenger(models.Model):
    name = models.CharField(max_length=30)
    emp_num = models.IntegerField(default=-1)
    num_pass = models.IntegerField(default=1)
    loc_pickup = models.CharField(max_length=100)
    loc_drop = models.CharField(max_length=100)
    req_hour = models.IntegerField(default=-1)
    req_min = models.IntegerField(default=-1)
    req_AMPM = models.CharField(max_length=2)
    asap_flag = models.BooleanField(default=False)
    driver_num = models.IntegerField(default=-1)
    push_id = models.IntegerField(default=-1)

    def to_dict(self):
        return { 'name' : self.name,
                 'employee_number' : self.emp_num, 
                 'num_passengers' : self.num_pass, 
                 'location_pickup' : self.loc_pickup, 
                 'location_dropoff' : self.loc_drop, 
                 'request_hour' : self.req_hour,
                 'request_min' : self.req_min,
                 'request_AMPM' : self.req_AMPM,
                 'driver_number' : self.driver_num }