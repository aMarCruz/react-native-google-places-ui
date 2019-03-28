# Changelog

## \[0.2.0] - 2019-03-27

### Added

- Support for the global `playServicesPlacesVersion` to specify the 'com.google.android.gms:play-services-places'. It defaults to 16.0.0

### Changed

- Using Build Tools v28.0.3 with `compileSdkVersion` and `targetSdkVersion` 28
- Using Gradle plugin 3.2.1
- Using Java 1.8
- Update Readme

## \[0.1.0-beta.2]

- Adds example
- Fixes typings to work with strict flags.
- Removed `PlaceTypes` enum, now `Place.placeTypes` is an array of strings.
- Renamed the `websiteUri` property of Place to `Place.website`.
- Removed dependency on jitpack.io

## \[0.1.0-beta.1]

- Initial commit
