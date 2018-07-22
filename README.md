# Kotlin dsl for OkHttp
[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp)

## Usage examples

### simple sync GET with `String.httpGet()`
```kotlin
val response: okhttp3.Response? = "https://yandex.ru/search/?text=iphone".httpGet()
```
   
### simple async GET with `String.asyncHttpGet()`
```kotlin
val response: okhttp3.Response? = "https://yandex.ru/search/?text=iphone".asyncHttpGet()
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

### async GET with dsl
*TODO*

### POST with dsl
*TODO*

## Customization

### Client pool customization
*TODO*
