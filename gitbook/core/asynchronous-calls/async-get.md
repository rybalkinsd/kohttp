# async GET

## **String.httpGetAsync\(\) extension function**

This function starts a new coroutine with _Unconfined_ dispatcher.

```kotlin
val response: Deferred<Response> = "https://google.com/search?q=iphone".httpGetAsync()
```

## **httpGetAsync call**

You can use same syntax as in [GET](../synchronous-calls/get.md)

```kotlin
val response: Deferred<Response> = httpGetAsync { }
```

