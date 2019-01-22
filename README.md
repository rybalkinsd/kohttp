# Kotlin dsl for OkHttp
[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp)
[![codecov](https://codecov.io/gh/rybalkinsd/kohttp/branch/master/graph/badge.svg)](https://codecov.io/gh/rybalkinsd/kohttp)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e072bcbe3dcf4fce87e44443f0721537)](https://www.codacy.com/app/yan.brikl/kohttp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rybalkinsd/kohttp&amp;utm_campaign=Badge_Grade)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.11-blue.svg)](https://kotlinlang.org) 
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin) [![Join the chat at https://gitter.im/kohttp/community](https://badges.gitter.im/kohttp/community.svg)](https://gitter.im/kohttp/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Kotlin DSL http client 

## Download

gradle kotlin DSL:
```kotlin
compile(group = "io.github.rybalkinsd", name = "kohttp", version = "0.8.0")
```

gradle groovy DSL:
```groovy
compile 'io.github.rybalkinsd:kohttp:0.8.0'
```

maven:
```xml
<dependency>
  <groupId>io.github.rybalkinsd</groupId>
  <artifactId>kohttp</artifactId>
  <version>0.8.0</version>
</dependency>
```


## Usage

### Sync http calls

#### GET

##### `String.httpGet()` extension

```kotlin
val response: Response = "https://google.com/search?q=iphone".httpGet()
```

##### GET with request parameters
```kotlin
val response: Response = httpGet {
   host = "google.com"
   path = "/search"
   param {
       "q" to "iphone"
       "safe" to "off"
   }
}
```

##### GET with header and cookies
```kotlin
val response: Response = httpGet {
    host = "github.com"
    path = "/search"

    header {
        "username" to "rybalkinsd"
        "security-policy" to json {
            "base-uri" to "none"
            "expect-ct" to json {
                "max-age" to 2592000
                "report-uri" to "foo.com/bar"
            }
            "script-src" to listOf("github.com", "github.io")
        }

        cookie {
            "user_session" to "toFycNV"
            "expires" to "Fri, 21 Dec 2018 09:29:55 -0000"
        }
    }

    param { ... }
}
```

#### POST

##### POST with `form` body.
`form` body has a `application/x-www-form-urlencoded` content type

```kotlin
val response: Response = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param { ... }
    header { ... }
    
    body {
        form {                              //  Resulting form will not contain ' ', '\t', '\n'
            "login" to "user"               //  login=user&
            "email" to "john.doe@gmail.com" //  email=john.doe@gmail.com
        }
    }
    // or
    body {
        form("login=user&email=john.doe@gmail.com")
    }
}
```


##### POST with `json` body.
`json` body has a `application/json` content type

```kotlin
val response: Response = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param { ... }
    header { ... }
    
    body {                                  //  Resulting json will not contain ' ', '\t', '\n'
        json {                              //  {
            "login" to "user"               //      "login": "user",
            "email" to "john.doe@gmail.com" //      "email": "john.doe@gmail.com" 
        }                                   //  }
    }
    // or
    body {
        json("""{"login":"user","email":"john.doe@gmail.com"}""")
    }
}
```

##### POST with various content type
In addition to `form` or `json` body content types it is possible to declare a custom content type.

`body` DSL support three data sources: `file()`, `bytes()` and `string()`

```kotlin
httpPost {
    body("application/json") {
        string("""{"login":"user","email":"john.doe@gmail.com"}""")
    }
}
```

```kotlin
val imageFile = File(getResource("/cat.gif").toURI())
httpPost {
    body(type = "image/gif") {
        file(imageFile)
    }
}
```

```kotlin
httpPost {
    body { // content type is optional, null by default
        bytes("string of bytes".toByteArray())
    }
}
```


   
#### HEAD

You can use same syntax as in [GET](#get)
```kotlin
val response = httpHead { }
```

#### PUT

You can use same syntax as in [POST](#post)
```kotlin
val response = httpPut { }
```

#### PATCH

You can use same syntax as in [POST](#post)
```kotlin
val response = httpPatch { }
```

#### DELETE

You can use same syntax as in [POST](#post)
```kotlin
val response = httpDelete { }
```

### Async http calls

#### async GET

##### `String.asyncHttpGet()` extension function
This function starts a new coroutine with *Unconfined* dispatcher. 

```kotlin
val response: Deferred<Response> = "https://google.com/search?q=iphone".asyncHttpGet()
```

##### `asyncHttpGet` call
```kotlin
val response: Deferred<Response> = asyncHttpGet {
    host = "google.com"
    path = "/search"
    header { ... }
    param { ... }
}
```

### Response usage
Kohttp methods return `okhttp3.Response` which is `AutoClosable` 
It's strictly recommended to access it with `use` to prevent resource leakage.  

```kotlin
val response = httpGet { ... }

reponse.use {
    ...
}
```


## Customization

### `defaultClientPool` customization
Kohttp provides a `defaultClientPool` to have a single endpoint for your http request.

It is possible to customize `defaultClientPool` by setting `kohttp.yaml` in resource directory of your project.

You can check default values in `io.github.rybalkinsd.kohttp.configuration.Config.kt`
*All time values are in Milliseconds*


```yaml
client:
  connectTimeout: 5000
  readTimeout: 10000
  writeTimeout: 10000
  followRedirects: true
  followSslRedirects: true
  connectionPool:
    maxIdleConnections: 42
    keepAliveDuration: 10000
```


### Fork `HttpClient` for specific tasks
Forked client uses **exactly the same** connection pool and dispatcher. However, it will custom parameters like custom `timeout`s, additional `interceptor`s or others.

In this example below `patientClient` will share `ConnectionPool` with `defaultHttpClient`, 
however `patientClient` requests will have custom read timeout.  
```kotlin
val patientClient = defaultHttpClient.fork {   
    readTimeout = 100_000 
}
```

### Run HTTP methods on custom client
If `defaultClientPool` or forked client does not suit you for some reason, it is possible to create your own one.

```kotlin
// a new client with custom dispatcher, connection pool and ping interval
val customClient = client {
    dispatcher = ...
    connectionPool = ConnectionPool( ... )
    pingInterval = 1_000
}
```

## Experimental

### Eager response
Instead of `.use { ... it.body?.string() ... }` it is now possible to read response body as string.
And also to map `Headers` to `listOf<Header>` to operate them easily.

```kotlin
val response: EagerResponse = "https://google.com/search?q=iphone".httpGet().eager()

// iterating over headers
response.headers.forEach { ... }

// manipulating body
response.body?.let { ... }

```

```kotlin
val response: EagerResponse = httpGet { }.eager()

// iterating over headers
response.headers.forEach { ... }

// manipulating body
response.body?.let { ... }
```
