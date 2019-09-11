# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased](https://github.com/rybalkinsd/kohttp/compare/0.11.0...HEAD)

## [0.11.0](https://github.com/rybalkinsd/kohttp/tree/0.11.0) - 2019-08-11

### Added
* Introduced multimodule structure [issue \#128](https://github.com/rybalkinsd/kohttp/issues/128)
* Split project into `kohttp` and `kohttp-jackson`
* Added full-featured Android sample [PR \#149](https://github.com/rybalkinsd/kohttp/pull/149) by [@IVSivak](https://github.com/IVSivak)
* Introduced cURL interceptor [issue \#139](https://github.com/rybalkinsd/kohttp/issues/139) by [@doyaaaaaken](https://github.com/doyaaaaaken)
* Introduced [gitbook](https://kohttp.gitbook.io/) by [@deviantBadge](https://github.com/DeviantBadge)
* Added full docs sync for gitbook

### Changed
* Improved Multipart DSL [issue \#132](https://github.com/rybalkinsd/kohttp/issues/132)
* [kohttp-jackson] Improved `toJson`, `toJsonOrNull`, `toType` methods [issue \#153](https://github.com/rybalkinsd/kohttp/issues/153)
* Redesigned simple logging interceptor to log both requests and responses [issue \#144](https://github.com/rybalkinsd/kohttp/issues/144)

### Removed
* Eager requests extensions [issue \#120](https://github.com/rybalkinsd/kohttp/issues/123)
* `kohttp.yaml` and it's configuration

### Big thanks

[@IVSivak](https://github.com/IVSivak), [@doyaaaaaken](https://github.com/doyaaaaaken), [@deviantBadge](https://github.com/DeviantBadge) for your contribution



## [0.10.0](https://github.com/rybalkinsd/kohttp/tree/0.10.0) - 2019-06-20

### Added

* Async Post DSL by [@Evgeny](https://github.com/DeviantBadge) [issue \#86](https://github.com/rybalkinsd/kohttp/issues/86).
* Async Head DSL by [@Evgeny](https://github.com/DeviantBadge).
* Async Put DSL by [@Evgeny](https://github.com/DeviantBadge).
* Async Patch DSL by [@Evgeny](https://github.com/DeviantBadge).
* Async Delete DSL by [@Evgeny](https://github.com/DeviantBadge).
* Async Upload DSL by [@Evgeny](https://github.com/DeviantBadge) [issue \#87](https://github.com/rybalkinsd/kohttp/issues/87).
* Async Upload File extensions by [@Evgeny](https://github.com/DeviantBadge).
* Async Upload Url extensions by [@Evgeny](https://github.com/DeviantBadge).
* Default `Dispatcher` configuration in `okhttp.yaml`
* CHANGELOG.md

### Changed

* Migrated to `kotlin` 1.3.40
* Migrated to `okhttp` 3.12.2 [issue \#81](https://github.com/rybalkinsd/kohttp/issues/81)
* Migrated to `kotlinx-coroutines-core` 1.2.1
* `Boolean` and Nullable types support in [Json builder](https://github.com/rybalkinsd/kohttp/blob/master/src/main/kotlin/io/github/rybalkinsd/kohttp/util/json.kt) 

  [issue \#113](https://github.com/rybalkinsd/kohttp/issues/113)

* `url()` and `param { }` joint usage by [@dtropanets](https://github.com/dtropanets) [issue \#94](https://github.com/rybalkinsd/kohttp/issues/94)
* Allowed nullable types in request `param { }` builder [issue \#118](https://github.com/rybalkinsd/kohttp/issues/118),

  [PR](https://github.com/rybalkinsd/kohttp/pull/117)

* Changed `maxRequests` and `maxRequestsPerHost` for default http client
* Async methods naming \(Deprecated `asyncHttpGet`\)
* Relaxed coverage [issue \#56](https://github.com/rybalkinsd/kohttp/issues/56)

### Removed

* pass

### Big thanks

[@deviantBadge](https://github.com/DeviantBadge), [@dtropanets](https://github.com/dtropanets), [@gokulchandra](https://github.com/gokulchandra) for your contribution

