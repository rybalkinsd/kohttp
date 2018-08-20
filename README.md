# Kotlin dsl for OkHttp
[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp)
[![codecov](https://codecov.io/gh/rybalkinsd/kohttp/branch/master/graph/badge.svg)](https://codecov.io/gh/rybalkinsd/kohttp)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e072bcbe3dcf4fce87e44443f0721537)](https://www.codacy.com/app/yan.brikl/kohttp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rybalkinsd/kohttp&amp;utm_campaign=Badge_Grade)
## Usage examples

### simple sync GET with `String.httpGet()`
```kotlin
val response: okhttp3.Response = "https://yandex.ru/search/?text=iphone".httpGet()

// Response is `AutoClosable` access it with `use` to prevent resource leakage
reponse.use {
    ...
}
```
   
### simple async GET with `String.asyncHttpGet()`
```kotlin
val response: Deferred<okhttp3.Response> = "https://yandex.ru/search/?text=iphone".asyncHttpGet()
```
   
### sync GET with `httpGet { }` dsl
```kotlin
val response: okhttp3.Response? = httpGet {
   host = "yandex.ru"
   path = "/search"
   param {
       "text" to "iphone"
       "lr" to 213
   }
}

reponse.use {
    ...
}
```

### sync GET with header and cookies with `httpGet { }` dsl
```kotlin
val response: okhttp3.Response? = httpGet {
    host = "postman-echo.com"
    path = "/get"

    val variable = 123L

    header {
        "one" to 42
        "two" to variable
        "three" to json {
            "a" to variable
            "b" to json {
                "b1" to "512"
            }
            "c" to listOf(1, 2.0, 3)
        }

        cookie {
            "aaa" to "bbb"
            "ccc" to 42
        }
    }

    param {
        "text" to "iphone"
        "lr" to 213
    }
}

reponse.use {
    ...
}
```
### async GET with dsl
*TODO*

### POST with dsl

#### Post with `form` body
`form` body has a `application/x-www-form-urlencoded` content type
```kotlin
val response: okhttp3.Response? = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param {
        "arg" to "iphone"
    }

    header {
        "one" to 42
        cookie {
            "aaa" to "bbb"
            "ccc" to 42
        }
    }
    
    body {
        form {                              //  Resulting form will not contain ' ', '\t', '\n'
            "login" to "user"               //  login=user&
            "email" to "john.doe@gmail.com" //  email=john.doe@gmail.com
        }
    }
}

reponse.use {
    ...
}
```

#### Post with `json` body
`json` body has a `application/json` content type
```kotlin
val response: okhttp3.Response? = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param {
        "arg" to "iphone"
    }

    header {
        "one" to 42
        cookie {
            "aaa" to "bbb"
            "ccc" to 42
        }
    }
    
    body {                                  //  Resulting json will not contain ' ', '\t', '\n'
        json {                              //  {
            "login" to "user"               //      "login": "user",
            "email" to "john.doe@gmail.com" //      "email": "john.doe@gmail.com" 
        }                                   //  }
    }
}

reponse.use {
    ...
}
```

## Customization

### Common Client pool customization
It is possible to customize CommonClientPool by setting up `kohttp.yaml` in resource directory of your project
All time values are in Milliseconds.
You can check default values in `com.kohttp.configuration.Config.kt`

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

### Run HTTP methods on custom client
TODO

### Fork Common Client for specific tasks
TODO



