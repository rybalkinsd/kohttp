# Kotlin dsl for OkHttp
[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp)
[![codecov](https://codecov.io/gh/rybalkinsd/kohttp/branch/master/graph/badge.svg)](https://codecov.io/gh/rybalkinsd/kohttp)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e072bcbe3dcf4fce87e44443f0721537)](https://www.codacy.com/app/yan.brikl/kohttp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rybalkinsd/kohttp&amp;utm_campaign=Badge_Grade)
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
