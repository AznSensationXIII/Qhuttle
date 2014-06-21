from django.conf.urls import patterns, url
from dispatcher import views

urlpatterns = patterns('',
    url(r'^add_driver', views.add_driver), 
    url(r'^remove_driver', views.remove_driver), 
    url(r'^enqueue', views.enqueue), 
    url(r'^dequeue', views.dequeue), 
    url(r'^notif', views.notif),
    url(r'^update', views.update_gps),  
    url(r'^fetch_driver', views.fetch_driver), 
)