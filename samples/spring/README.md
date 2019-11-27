# kohttp-spring sample

## Description
This is a basic application showing **kohttp** usage together with **Spring Boot**. 
We are using Spring Boot 2.x, however, Spring Boot 1.x should work, except Spring MVC Router DSL.

Application allows to check temperature in chosen location.


## Configuration
- **kohttp** specific configurations is in `WeatherApplication` class.
- Spring Boot context:
    - `WeatherController` 
    - `LocationController`
    - `LocationRepo` 
    - `MockLogService` 
 
## Checking /weather
Open `localhost:8080/weather` in your browser.
Or use cURL
```bash
curl -X GET http://localhost:8080/weather
```

*WeatherController* use **kohttp** to access third party REST API with weather data.

*WeatherController* also use **kohttp** to log weather asynchronously using a specific client: `extendedTimeoutClient`.   


## Accessing /location
*LocationController* expose API to get and modify you location.

Open `localhost:8080/location` in your browser.
Or use cURL
```bash
curl -X GET http://localhost:8080/location
``` 

Changing location using POST request. 
```bash 
curl -X POST \
  http://localhost:8080/location \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d location=Moscow
```

## Check /weather/log
Open `localhost:8080/weather/log` in your browser.
Or use cURL
```bash
curl -X GET http://localhost:8080/weather/log
```

Note: MockLogService is designed in the way that it emulates eventual consistent behaviour.
Logs get accounted with a specific delay and could be reordered. 


## Build
Build sample as any other SpringBoot application.  
```bash
./gradlew clean bootJar 
```

## Run
Run sample as any other SpringBoot application.
```bash
./gradlew bootRun
```
