from dispatcher.models import Driver, Passenger
from django.shortcuts import render
from django.http import HttpResponse
from django.core.serializers.json import DjangoJSONEncoder
import json

# Create your views here.

def add_driver(request):
    if request.method == 'POST':
        try: 
            info = json.loads(request.body)
            driver = Driver( name=info['name'],
                             employnum=info['employee_number'],
                             lati=info['latitude'],
                             longi=info['longitude'] )
            driver.save()
            return HttpResponse(status=200)
        except KeyError:
            return HttpResponse(status=400)

def remove_driver(request):
    if request.method == "POST":
        try:
            info = json.loads(request.body)
            driver = Driver.objects.get(e_num=info['employee_number'])
            driver.delete()
            return HttpResponse(status=200)
        except Dispatcher.DoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)

def enqueue(request):
    if request.method == 'POST':
        try:
            info = json.loads(request.body)
            passenger = Passenger( name=info['name'],
                                   emp_num=info['employee_number']
                                   num_pass=info['num_passengers'],
                                   loc_pickup=info['location_pickup'],
                                   loc_drop=info['location_dropoff'],
                                   req_time=info['request_time'],
                                   asap_flag=info['asap_flag'],
                                   driver_num=info['driver_number'],
                                   push_id=info['push_id'] )
            passenger.save()
            return HttpResponse(status=200)
        except KeyError:
            return HttpResponse(status=400)

def dequeue(request):
    if request.method == 'POST':
        try:
            info = json.loads(request.body)
            passenger = Passenger.objects.get(emp_num=info['employee_number'])
            if passenger.driver_num == -1:
                passenger.driver_num = info['driver_num']
                ret = passenger.to_dict()
                ret['response'] = 'SUCCESS'
                return HttpResponse(json.dumps(ret))
            else:
                return HttpResponse(json.dumps({'response' : 'FAILED'}))
        except Dispatcher.DoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)

def notif(request):
    if request.method == 'POST':
        

