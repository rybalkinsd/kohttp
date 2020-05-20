# Generic requests

A request context can be reused for different http methods without explicitly calling the `httpGet` or other functions for different http methods.

```kotlin
var method: Method = Method.GET

val response: Response = http(method) {
    host = "google.com"
    path = "/search"
    param {
        "q" to "iphone"
        "safe" to "off"
    }
}
```
