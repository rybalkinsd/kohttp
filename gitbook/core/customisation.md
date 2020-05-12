# Customisation

## `defaultClientPool` customization

Kohttp provides a `defaultClientPool` to have a single endpoint for your http request.

## Fork `HttpClient` for specific tasks

Forked client uses **exactly the same** connection pool and dispatcher. However, it will custom parameters like custom `timeout`s, additional `interceptor`s or others.

In this example below `patientClient` will share `ConnectionPool` with `defaultHttpClient`, however `patientClient` requests will have custom read timeout.

```kotlin
val patientClient = defaultHttpClient.fork {   
    readTimeout = 100_000 
}
```

## Run HTTP methods on custom client

If `defaultClientPool` or forked client does not suit you for some reason, it is possible to create your own one.

```kotlin
// a new client with custom dispatcher, connection pool and ping interval
val customClient = client {
    dispatcher = ...
    connectionPool = ConnectionPool( ... )
    pingInterval = 1_000
    sslConfig = SslConfig().apply {
        socketFactory = ...
        sslSocketFactory = ...
        trustManager = ...
        hostnameVerifier = ...
        certificatePinner = ...
        followSslRedirects = ...
    }
}
```

