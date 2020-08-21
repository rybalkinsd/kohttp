# Response usage

Kohttp methods return `okhttp3.Response` which is `AutoClosable` It's strictly recommended to access it with `use` to prevent resource leakage.

```kotlin
val response = httpGet { ... }

response.use {
    ...
}
```

Response body can be retrieved as a `JSON`, `String` or `InputStream` using provided extension functions on `Response`.

```kotlin
val response = httpGet { ... }

val dataAsJson: JsonNode = response.asJson()

val dataAsString: String? = response.asString()

val dataAsStream: InputStream? = response.asStream()
```

