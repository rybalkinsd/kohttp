# kohttp-spring sample

## Description
This is a basic application showing kohttp usage together with Spring Boot. 
We are using Spring Boot 2.x, however, Spring Boot 1.x uses similar ideas, except Spring MVC Router DSL.

Application expose 4 endpoints on `localhost:8080`
- GET /foo
- POST /foo
- GET /foobar
- POST /foobar

Each endpoint perform various requests using `kohttp`. 
Both GET endpoints use defaultHttpClient as a client for `kohttp` requests.
However, both POST endpoints use custom http client with extended `readTimeout` and logging interceptor   

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

## Test

### GET requests
Open `localhost:8080/foo` or `localhost:8080/foobar` in your browser.
Or use cURL
```bash
curl -X GET http://localhost:8080/foo
curl -X GET http://localhost:8080/foobar
```

### POST requests 
Use cURL
```bash
curl -X POST http://localhost:8080/foo
curl -X POST http://localhost:8080/foobar
```