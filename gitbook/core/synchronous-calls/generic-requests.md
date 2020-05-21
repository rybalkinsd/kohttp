# Generic requests

A request context can be reused for different http methods without explicitly calling the `httpGet` or other functions for different http methods.

```kotlin
var variableMethod: Method = Method.GET

val response: Response = http(method = variableMethod) {
    host = "google.com"
    path = "/search"
    param {
        "q" to "iphone"
        "safe" to "off"
    }
}
```

or

```kotlin
var variableMethod: Method = Method.GET
val context : HttpContext.() -> Unit = {
    host = "google.com"
    path = "/search"
    param {
        "q" to "iphone"
        "safe" to "off"
    }
}

val response: Response = http(method = variableMethod, init = context)
```