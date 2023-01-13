# Coding Challenge Backend Developer 

## Solution
### StationsController: ( request handling)
The controller is responsible to handle the incoming GET-Requests, it hands over two given   
ds100 parameters to the StationsService

### StationsService: ( business logic layer)
The service is the business logic layer  
This Layer request the data with the given ds100 codes from the StationsRepositroy.  
With the received data the distance between these two destinations is calculated and the dataset
is transformed to be represented in REST response

### StationsRepository: ( data layer )
The repository is responsible for handling the data  
The underlaying CSV is only loaded once on startup and it only maps needed data

### StationsNotFoundException
The StationNotFoundException is thrown if one input parameter is incorrect 

## UnitTests
### StationsServiceTest:
Test if the DistanceResponse is correct  
Test if the StationNotFoundException is thrown if input parameter is incorrect

## IntegrationTests

### StationControllerIT
This test start the whole application and tests if the response for the given request URL is correct

## What could be done better?

If CSV file changes, the service needs to be deployed to see the changes.  
If more than one instance of the service is running, the CSV should be shared between the services (Redis - in-memory data structure store).



## Testlinks
http://localhost:8080/api/v1/distance/FF/BL
http://localhost:8080/api/v1/distance/HEBA/KRO