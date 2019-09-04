# Upload files

## **Upload DSL**

You can upload file by `URI` or `File` . Upload DSL can include `headers` and `params`.

```kotlin
val fileUri = this.javaClass.getResource("/cat.gif").toURI()

val response = upload {
    url("http://postman-echo.com/post")
    file(fileUri)
    headers {
            ...
            cookies {...}
        }
    params {...}
}
```

## **Upload File extensions**

```kotlin
val file = File(this.javaClass.getResource("/cat.gif").toURI())
val response = file.upload( string or url )
```

## **Upload URI extensions**

```kotlin
val fileUri = this.javaClass.getResource("/cat.gif").toURI()
val response = fileUri.upload( string or url )
```

