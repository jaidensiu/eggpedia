# Eggpedia

A Kotlin Multiplatform Android and iOS mobile app to explore recipes and play minigames about eggs.

## Table of contents

- [Motivation](#motivation)
- [My experience building with Kotlin Multiplatform](#my-experience-building-with-kotlin-multiplatform)
- [Features](#features)
- [Architecture](#architecture)
- [Built with](#built-with)
- [How to run project](#how-to-run-project)
- [License](#license)

## Motivation

As someone who likes to eat eggs (and write code in Kotlin), I envisioned an Android and iOS app about eggs using Kotlin Multiplatform to combine my unique interests.

## My experience building with Kotlin Multiplatform

TODO: write about my experience with KMP

## Features

TODO: add images

- Home screen
- Egg recipes screen
- Saved/cached egg recipes screen
- Egg details screen
- Egg minigames
  - Egg recipe matching minigame
  - Egg memory matching minigame

## Architecture

The architecture mirrors the recommended architectural principles and best practices for building clean, scalable, and maintainable apps for Android.

Modules

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
- Konnectivity

## How to run project

These steps assume that your environment is set up for both Android and iOS development.

Targets

- Android
  - Open the project in Android studio and go through the run configurations
- iOS
  - Open the project in Xcode and go through the run configurations

# License

TODO: add license
