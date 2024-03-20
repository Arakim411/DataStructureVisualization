# Data Structure Visualization
Purpose of the app is to create generic tool which allows easily and in declarative way visualize an algorithm. 

# Example alghoritms

### Binary search tree
[Screen_recording_20240319_171601.webm](https://github.com/Arakim411/DataStructureVisualization/assets/64414992/5ac4439e-df82-4520-a0bc-1fe94821e0a8)


### Hash map
[Screen_recording_20240319_164305.webm](https://github.com/Arakim411/DataStructureVisualization/assets/64414992/d7b282b8-b30c-40fd-8f33-81afda6781ca)

# Tech stack

## Core
- 100% Kotlin
- 100% Jetpack Compose
- Material3
- Kotlin Coroutines
- Kotlin Flow
- Hilt

## Local Persistence
Room DB

## Build
- Gradle Kts
- Gradle version catalogs 

## Other
- ktlint

# Modules architecture

<img width="1454" alt="visualize_data_structurs_modules_diagram" src="https://github.com/Arakim411/DataStructureVisualization/assets/64414992/a6d34f41-ee89-40bc-a1e9-83fca4840686">

## Modules Overview

Essentially, we wouldn't need data and domain layer, but since we save data structures and visualization setups in Room DB, we are utilizing it. Therefore, I won't delve deeply into the domain and data layers, as it's common practice to have repositories in the domain and their implementations in the data layer.

## Visualization Builder 

Core feature of the application, allowing in declarative way to visualize data structures. Look at example usage in the **Binary Search Tree Screen**.

(Arrow direction shows dependency)

<img width="858" alt="Screenshot 2024-03-20 at 14 18 48" src="https://github.com/Arakim411/DataStructureVisualization/assets/64414992/5d33ad69-8456-42fd-9025-8dddfa1e5a43">

For example, the flow visualization of adding nodes looks like this:

1. Compose notifies the screen controller that the user wants to add a node.
2. The screen controller adds it in the BST data structure.
3. The BST data structure updates its own state and triggers listener in the screen controller.
4. The screen controller notifies the visualization builder to draw the added value.

![Screenshot 2024-03-20 at 14 07 43](https://github.com/Arakim411/DataStructureVisualization/assets/64414992/0cfca07b-7d60-435c-b120-28b2e9792f3a)

Visualization of every other data structure looks similar:

1. Create UI controller and compose screen which handles data structures specific logic, e.g., in BST, we need to show a dialog where the user can add or delete nodes. (Compose Screen, Screen controller)
2. Create helper to handle data structure logic (Binary search tree data structure)
3. When data structure updates, pass declarative information about what should be drawn to visualization builder.

