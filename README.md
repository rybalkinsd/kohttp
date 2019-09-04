# Introduction

![](https://repository-images.githubusercontent.com/141764280/12771480-a7f9-11e9-8a41-4a1280106f8a)

[![Build Status](https://travis-ci.org/rybalkinsd/kohttp.svg?branch=master)](https://travis-ci.org/rybalkinsd/kohttp) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.rybalkinsd/kohttp) [![codecov](https://codecov.io/gh/rybalkinsd/kohttp/branch/master/graph/badge.svg)](https://codecov.io/gh/rybalkinsd/kohttp) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/e072bcbe3dcf4fce87e44443f0721537)](https://www.codacy.com/app/yan.brikl/kohttp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rybalkinsd/kohttp&amp;utm_campaign=Badge_Grade) [![Kotlin](https://img.shields.io/badge/Kotlin-1.3.40-blue.svg)](https://kotlinlang.org) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin) [![Join the chat at https://gitter.im/kohttp/community](https://badges.gitter.im/kohttp/community.svg)](https://gitter.im/kohttp/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)[![star this repo](http://githubbadges.com/star.svg?user=rybalkinsd&repo=kohttp&style=flat)](https://github.com/rybalkinsd/kohttp)

Kotlin DSL http client based on okhttp

## Features

 🔹 HTTP [`GET`](gitbook/core/synchronous-calls/get.md)/[`POST`](gitbook/core/synchronous-calls/post.md)/[`PUT`](gitbook/core/synchronous-calls/put.md)/[`HEAD`](gitbook/core/synchronous-calls/head.md)/[`DELETE`](gitbook/core/synchronous-calls/delete.md)/[`PATCH`](gitbook/core/synchronous-calls/patch.md) requests   
 🔹 [Asynchronous](gitbook/core/asynchronous-calls/) and [blocking](gitbook/core/synchronous-calls/) requests  
 🔹 [Upload files](gitbook/core/synchronous-calls/upload-files.md)  
 🔹 [Logging](gitbook/core/interceptors.md#logging-interceptor-a-request-logging-interceptor) with an opportunity to convert kohttp call to cUrl call  
 🔹 Simple and convenient call creation via kotlin DSL

## Installation

### Gradle

Kotlin DSL:

```kotlin
implementation(group = "io.github.rybalkinsd", name = "kohttp", version = "0.10.0")
```

Groovy DSL:

```groovy
implementation 'io.github.rybalkinsd:kohttp:0.10.0'
```

### Maven:

```markup
<dependency>
  <groupId>io.github.rybalkinsd</groupId>
  <artifactId>kohttp</artifactId>
  <version>0.10.0</version>
</dependency>
```

## Quick start

Kohttp features can be simply used right after moment when it was added to project dependencies:

```kotlin
// synchronous get call
val users: Response = "https://my-host.com/users?admin=true".httpGet()
// transformation to json
val usersAsJson: JsonNode = users.asJson()

// async calls, collected to one array
val sentMessages: List<Deferred<Response>> = usersAsJson.map {
    httpPostAsync {
        url("https://my-host.com/friends/add")
        
        param {
            "userId" to it.id
            "newFriendName" to "admin"
        }
        
        header {
            "locale" to "ru_RU"
            cookie {
                "user_session" to "toFycNV"
                "authToken" to "d2dwa6011w96c93ct3e3493d4a1b5c8751563217409"
            }
        }
    }
}

// returns List<Response>
return runBlocking {
    sentMessages.awaitAll()
}
```

## Publications

* [Kotlin weekly](https://mailchi.mp/kotlinweekly/kotlin-weekly-124) mentioned us
* [Android weekly \(CN\)](https://androidweekly.io/android-dev-weekly-issue-208/) mentioned us
* [Write your Android network as Kotlin DSL](https://medium.com/datadriveninvestor/write-your-android-networking-as-a-kotlin-dsl-330febae503f) - medium post
* [Upload files to Google Drive with Kotlin](https://medium.com/@sergei.rybalkin/upload-file-to-google-drive-with-kotlin-931cec5252c1) - medium post
* [Production Kotlin DSL \(RU + subtitles\) ](https://youtu.be/4m9bS0M0Nww) - Kotlin/Everywhere talk, YouTube

## Table of contents

* [Synchronous calls](gitbook/core/synchronous-calls/)
  * [GET](gitbook/core/synchronous-calls/get.md)
  * [POST](gitbook/core/synchronous-calls/post.md)
  * [PUT](gitbook/core/synchronous-calls/put.md)
  * [HEAD](gitbook/core/synchronous-calls/head.md)
  * [DELETE](gitbook/core/synchronous-calls/delete.md)
  * [PATCH](gitbook/core/synchronous-calls/patch.md)
  * [Upload files](gitbook/core/synchronous-calls/upload-files.md)
* [Asynchronous calls](gitbook/core/asynchronous-calls/)
  * [async GET](gitbook/core/asynchronous-calls/async-get.md)
  * [async POST](gitbook/core/asynchronous-calls/async-post.md)
  * [async PUT](gitbook/core/asynchronous-calls/async-put.md)
  * [async HEAD](gitbook/core/asynchronous-calls/async-head.md)
  * [async DELETE](gitbook/core/asynchronous-calls/async-delete.md)
  * [async PATCH](gitbook/core/asynchronous-calls/async-patch.md)
  * [async Upload files](gitbook/core/asynchronous-calls/async-upload-files.md)
* [Response usage](gitbook/core/response-usage.md)
* [Interceptors](gitbook/core/interceptors.md)
* [Customisation](gitbook/core/customisation.md)
* [Experimental features](gitbook/core/experimental-features.md)
* [Changelog](gitbook/history/changelog.md)

