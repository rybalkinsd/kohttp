# GET

Get requests can be made in two different ways. First one is string extension, second is via kotlin dsl.

## **String.httpGet\(\) extension**

```kotlin
val response: Response = "https://google.com/search?q=iphone".httpGet()
```

## **GET with request parameters**

```kotlin
val response: Response = httpGet {
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
val response: Response = httpGet {
   url("https://google.com/search")
   param {
       "q" to "iphone"
       "safe" to "off"
   }
}
```

## **GET with header and cookies**

```kotlin
val response: Response = httpGet {
    host = "github.com"
    path = "/search"

    header {
        "username" to "rybalkinsd"
        "security-policy" to json {
            "base-uri" to "none"
            "expect-ct" to json {
                "max-age" to 2592000
                "report-uri" to "foo.com/bar"
            }
            "script-src" to listOf("github.com", "github.io")
        }

        cookie {
            "user_session" to "toFycNV"
            "expires" to "Fri, 21 Dec 2018 09:29:55 -0000"
        }
    }

    param { ... }
}
```

