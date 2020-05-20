# async Generic requests

A request context can be reused for different http methods without explicitly calling the `httpGetAsync` or other functions for different http methods.

```kotlin
var variableMethod: Method = Method.GET

val response: Deferred<Response> = httpAsync(method = variableMethod) {
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

val response: Deferred<Response> = httpAsync(method = variableMethod, init = context)
```
