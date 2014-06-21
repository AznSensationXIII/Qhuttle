from dispatcher.models import Driver, Passenger
from django.shortcuts import render
from django.http import HttpResponse
from django.core.serializers.json import DjangoJSONEncoder
from django.core.exceptions import ObjectDoesNotExist
import sys, json
from push_notifications.models import GCMDevice

# Create your views here.

def add_driver(request):
    if request.method == 'POST':
        info = json.loads(request.body)
        try: 
            driver = Driver.objects.get(employnum=info['employee_number'])
            return HttpResponse(status=400)
        except ObjectDoesNotExist:
            driver = Driver( name=info['name'],
                             employnum=info['employee_number'],
                             lati=info['latitude'],
                             longi=info['longitude'] )
            driver.save()
            return HttpResponse(status=200)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def remove_driver(request):
    if request.method == "POST":
        try:
            info = json.loads(request.body)
            driver = Driver.objects.get(employnum=info['employee_number'])
            driver.delete()
            return HttpResponse(status=200)
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def enqueue(request):
    if request.method == 'POST':
        info = json.loads(request.body)
        try:
            passenger = Passenger.objects.get(emp_num=info['employee_number'])
            return HttpResponse(status=400)
        except ObjectDoesNotExist:
            passenger = Passenger( name=info['name'],
                                   emp_num=info['employee_number'],
                                   num_pass=info['num_passengers'],
                                   loc_pickup=info['location_pickup'],
                                   loc_drop=info['location_dropoff'],
                                   req_hour=info['request_hour'],
                                   req_min=info['request_minute'], 
                                   req_AMPM=info['request_AMPM'],
                                   asap_flag=info['asap_flag'],
                                   driver_num=info['driver_number'],
                                   push_id=info['push_id'] )
            passenger.save()
            return HttpResponse(status=200)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def dequeue(request):
    if request.method == 'POST':
        try:
            info = json.loads(request.body)
            passenger = Passenger.objects.get(emp_num=info['employee_number'])
            if passenger.driver_num == -1:
                passenger.driver_num = info['driver_num']
                passenger.save()
                ret = passenger.to_dict()
                ret['response'] = 'SUCCESS'
                return HttpResponse(json.dumps(ret))
            else:
                return HttpResponse(json.dumps({'response' : 'FAILED'}))
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def notif(request):
    if request.method == 'POST':
        try:
            info = json.loads(request.body)
            passenger = Passenger.objects.get(emp_num=info['employee_number'])
            device = GCMDevice.objects.get(registration_id=info['push_id'])
            device.send_message({"message": "You're driver will arrive within five minutes."})
            passenger.delete()
            return HttpResponse(status=200)
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def update_gps(request):
    if request.method == 'POST':
        try:
            info = json.loads(request.body)
            driver = Driver.objects.get(employnum=info['employee_number'])
            driver.lati = info['longitude']
            driver.longi = info['latitude']
            driver.save()
            return HttpResponse(status=200)
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def fetch_driver(request):
    if request.method == 'GET':
        try:
            info = json.loads(request.body)
            passenger = Passenger.objects.get(emp_num=info['employee_number'])
            if passenger.emp_num == -1:
                return HttpResponse(status=400)
            else:
                driver = Driver.objects.get(employnum=info['employee_number'])
                return HttpResponse(driver.to_dict())
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

def refresh(request):
    if request.method == 'GET':
        try:
            result = {'asap' : [], 'scheduled' : []}
            info = Passenger.objects.all()
            asaps = info.filter(driver_num=-1,asap_flag=True)
            for obj in asaps:
                result['asap'].append(obj.to_dict())
            scheds = info.filter(driver_num=-1,asap_flag=False)
            for obj in scheds:
                result['scheduled'].append(obj.to_dict())
            return HttpResponse(json.dumps(result))
        except ObjectDoesNotExist:
            return HttpResponse(status=400)
        except KeyError:
            return HttpResponse(status=400)
    return HttpResponse(status=418)

