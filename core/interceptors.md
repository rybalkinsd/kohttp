# Interceptors

Kohttp provides a DSL to add interceptors. Custom Interceptors can be defined by implementing the `okhttp3.Interceptors`. Interceptors are added by forking the `defaultHttpClient`.

```kotlin
val forkedClient = defaultHttpClient.fork {
    interceptors {
          +interceptor1
          +interceptor2
    }
 }
```

## Logging Interceptor: A Request Logging Interceptor.

Parameters: 1. `strategy: LoggingStrategy = HttpLoggingStrategy()`: Formatting strategy: CURL / HTTP 2. `log: (String) -> Unit = ::println`: function as a parameter to consume the log message. It defaults to `println`. Logs Request body when present.

Usage:

```kotlin
val client = defaultHttpClient.fork {
                interceptors {
                    +LoggingInterceptor()
                }
            }
```

Sample Output: `[2019-01-28T04:17:42.885Z] GET 200 - 1743ms https://postman-echo.com/get`

## Retry Interceptor: Provides a configurable method to retry on specific errors.

Parameters:

1. `failureThreshold: Int`:  Number of attempts to get response with. Defaults to `3`.
2. `invocationTimeout: Long`: timeout \(millisecond\) before retry. Defaults to `0`
3. `ratio: Int`: ratio for exponential increase of invocation timeout. Defaults to `1`
4. `errorStatuses: List<Int>`: HTTP status codes to be retried on. Defaults to listOf\(503, 504\)  

Usage:

```kotlin
val client = defaultHttpClient.fork {
                interceptors {
                    +RetryInterceptor()
                }
            }
```

## Signing Interceptor: Enables signing of query parameters. Allowing creation of presigned URLs.

Parameters: 1. `parameterName: String`: The name of the parameter with signed key 2. `signer: HttpUrl.() -> String`: Function with `okhttp3.HttpUrl` as a receiver to sign the request parameter

Usage:

```kotlin
val client = defaultHttpClient.fork {
                interceptors {
                    +SigningInterceptor("key") {
                        val query = (query() ?: "").toByteArray()
                        urlEncoder.encodeToString(md5.digest(query))
                    }
                }
            }
```

