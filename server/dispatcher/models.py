from django.db import models

# Create your models here.
class Driver(models.Model): 
	name = models.CharField(max_length=30)
	employnum = models.IntegerField()
	picture = models.ImageField()
	lati = models.FloatField()
	longi = models.FloatField()
