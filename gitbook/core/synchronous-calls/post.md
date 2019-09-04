# POST

Post requests can be made only via kotlin dsl. For post calls kohttp will set content type by rule that mentioned below.

## **Content type priority**

Content type is set according to the following priority levels \(higher is prioritized\)

1. Form or Json in body :  `body() { json { ... } } ...`
2. Custom body type : `body(myContentType) { ... } ...`
3. Header : `header { "Content-type" to myContentType } ...`

## **POST with form body.**

`form` body has a `application/x-www-form-urlencoded` content type

```kotlin
val response: Response = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param { ... }
    header { ... }

    body {
        form {                              //  Resulting form will not contain ' ', '\t', '\n'
            "login" to "user"               //  login=user&
            "email" to "john.doe@gmail.com" //  email=john.doe@gmail.com
        }
    }
    // or
    body {
        form("login=user&email=john.doe@gmail.com")
    }
}
```

## **POST with json body.**

`json` body has a `application/json` content type

```kotlin
val response: Response = httpPost {
    host = "postman-echo.com"
    path = "/post"

    param { ... }
    header { ... }

    body {                                  //  Resulting json will not contain ' ', '\t', '\n'
        json {                              //  {
            "login" to "user"               //      "login": "user",
            "email" to "john.doe@gmail.com" //      "email": "john.doe@gmail.com" 
        }                                   //  }
    }
    // or
    body {
        json("""{"login":"user","email":"john.doe@gmail.com"}""")
    }
}
```

## **POST with various content type**

In addition to `form` or `json` body content types it is possible to declare a custom content type.

`body` DSL support three data sources: `file()`, `bytes()` and `string()`

```kotlin
httpPost {
    body("application/json") {
        string("""{"login":"user","email":"john.doe@gmail.com"}""")
    }
}
```

```kotlin
val imageFile = File(getResource("/cat.gif").toURI())
httpPost {
    body(type = "image/gif") {
        file(imageFile)
    }
}
```

```kotlin
httpPost {
    body { // content type is optional, null by default
        bytes("string of bytes".toByteArray())
    }
}
```

### **POST with multipart body**

```kotlin
val response = httpPost {
    url("http://postman-echo.com/post")

    multipartBody {
        +part("meta") {
            json {
                "token" to "$token"
            }
        }
        +part("image") {
            file(imageFile)
        }
    }
}
```

