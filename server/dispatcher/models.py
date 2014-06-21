from django.db import models

# Create your models here.
class Driver(models.Model): 
	name = models.CharField(max_length=30)
	employnum = models.IntegerField()
	picture = models.ImageField()
	lati = models.FloatField()
	longi = models.FloatField()

class Passenger(models.Model):
	name = models.CharField(max_length=30)
	num_pass = models.IntegerField(default=1)
	loc_pickup = models.CharField(max_length=100)
	loc_drop = models.CharField(max_length=100)
	req_time = models.TimeField('Request Time')
	asap_flag = models.BooleanField(default=False)
	drive = models.CharField(max_length=30, default='')
	push_id = models.IntegerField(default=-1)