# Kotlin dsl for OkHttp
[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp)
[![Coverage Status](https://coveralls.io/repos/github/rybalkinsd/kohttp/badge.svg?branch=master)](https://coveralls.io/github/rybalkinsd/kohttp?branch=master)
## Usage examples

### simple sync GET with `String.httpGet()`
```kotlin
val response: okhttp3.Response? = "https://yandex.ru/search/?text=iphone".httpGet()
```
   
### simple async GET with `String.asyncHttpGet()`
```kotlin
val response: Deferred<okhttp3.Response?> = "https://yandex.ru/search/?text=iphone".asyncHttpGet()
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
```
### async GET with dsl
*TODO*

### POST with dsl
*TODO*

## Customization

### Client pool customization
*TODO*
