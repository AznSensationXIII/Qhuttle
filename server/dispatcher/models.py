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
                 'employee_number' : self.employnum,
                 'latitude' : self.lati,
                 'longitude' : self.longi }

class Passenger(models.Model):
    name = models.CharField(max_length=30)
    emp_num = models.IntegerField(default=-1)
    num_pass = models.IntegerField(default=1)
    loc_pickup = models.CharField(max_length=100)
    loc_drop = models.CharField(max_length=100)
    req_time = models.TimeField('Request Time')
    asap_flag = models.BooleanField(default=False)
    driver_num = models.IntegerField(default=-1)
    push_id = models.IntegerField(default=-1)

