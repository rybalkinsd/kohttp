# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.10.0] - 2019-06-20
### Added
- Async Post DSL by [@Evgeny](https://github.com/DeviantBadge) [issue #86](https://github.com/rybalkinsd/kohttp/issues/86).
- Async Head DSL by [@Evgeny](https://github.com/DeviantBadge).
- Async Put DSL by [@Evgeny](https://github.com/DeviantBadge).
- Async Patch DSL by [@Evgeny](https://github.com/DeviantBadge).
- Async Delete DSL by [@Evgeny](https://github.com/DeviantBadge).
- Async Upload DSL by [@Evgeny](https://github.com/DeviantBadge) [issue #87](https://github.com/rybalkinsd/kohttp/issues/87).
- Async Upload File extensions by [@Evgeny](https://github.com/DeviantBadge).
- Async Upload Url extensions by [@Evgeny](https://github.com/DeviantBadge).
- Default `Dispatcher` configuration in `okhttp.yaml`
- CHANGELOG.md

### Changed
- Migrated to `kotlin` 1.3.40
- Migrated to `okhttp` 3.12.2 [issue #81](https://github.com/rybalkinsd/kohttp/issues/81)
- Migrated to `kotlinx-coroutines-core` 1.2.1
- `Boolean` and Nullable types support in [Json builder](https://github.com/rybalkinsd/kohttp/blob/master/src/main/kotlin/io/github/rybalkinsd/kohttp/util/json.kt) 
[issue #113](https://github.com/rybalkinsd/kohttp/issues/113)
- `url()` and `param { }` joint usage by [@dtropanets](https://github.com/dtropanets) [issue #94](https://github.com/rybalkinsd/kohttp/issues/94)
- Allowed nullable types in request `param { }` builder [issue #118](https://github.com/rybalkinsd/kohttp/issues/118),
[PR](https://github.com/rybalkinsd/kohttp/pull/117)
- Changed `maxRequests` and `maxRequestsPerHost` for default http client
- Async methods naming (Deprecated `asyncHttpGet`)
- Relaxed coverage [issue #56](https://github.com/rybalkinsd/kohttp/issues/56)
 

### Removed
 - pass
 
### Big thanks 
 @deviantBadge, @dtropanets, @gokulchandra for your contribution   
