# Eggpedia

A Kotlin Multiplatform Android and iOS mobile app to explore recipes and play minigames about eggs.

## Table of contents

- [Motivation](#motivation)
- [My experience building with Kotlin Multiplatform](#my-experience-building-with-kotlin-multiplatform)
- [Features](#features)
- [Architecture](#architecture)
  - [Modules](#modules)
- [Built with](#built-with)
- [How to run project](#how-to-run-project)
  - [Android](#android)
  - [iOS](#ios)
- [License](#license)

## Motivation

As someone who likes to eat eggs (and write code in Kotlin), I envisioned an Android and iOS app about eggs using Kotlin Multiplatform to combine my unique interests.

## My experience building with Kotlin Multiplatform

TODO

## Features

- Home screen
- Egg recipes screen
- Saved/cached egg recipes screen
- Egg details screen
- Egg minigames
  - Egg recipe matching minigame
  - Egg memory matching minigame

## Architecture

The architecture mirrors the recommended architectural principles and best practices for building clean, scalable, and maintainable apps on Android.

### Modules

- `composeApp`
  - A Kotlin module that contains the logic shared among the Android and iOS apps
- `iosApp`
  - An Xcode project that builds into an iOS application that depends on and uses the shared module as an iOS framework

## Built with

- Kotlin
- Gradle
- Kotlin Multiplatform
- Compose Multiplatform
- Room
- SQLite
- Ktor
- Koin
- Coil

## How to run project

These steps assume that your development environment is set up for both Android and iOS on a MacOS-based machine. You can also run the Android or iOS app on Android Studio with the run button wherever there is `composeApp` and `iosApp`. 

- Android
  - Open the project in Android studio and go through the run configurations
- iOS
  - Open the project in Xcode and go through the run configurations

# License

TODO
