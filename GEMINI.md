# Project Overview

This is a native Android application written in Kotlin that displays a catalog of Rick and Morty characters. The app is built using modern Android development practices and libraries.

**Key Technologies:**

*   **UI:** Jetpack Compose
*   **Dependency Injection:** Hilt
*   **Networking:** Retrofit
*   **Paging:** Paging 3
*   **Architecture:** MVVM (Model-View-ViewModel)

**Project Structure:**

The project is organized into the following main packages:

*   `data`: Contains the data layer, including the network API client, repository, and data models.
*   `domain`: Contains the business logic of the application, including use cases and domain models.
*   `ui`: Contains the presentation layer, including Composable screens, ViewModels, and navigation.

# Building and Running

To build and run the project, you can use Android Studio or the Gradle wrapper.

**Using Android Studio:**

1.  Open the project in Android Studio.
2.  Click the "Run" button.

**Using Gradle:**

1.  Open a terminal in the project's root directory.
2.  Run the following command:

    ```bash
    ./gradlew assembleDebug
    ```

# Development Conventions

*   **Coding Style:** The project follows the official Kotlin coding conventions.
*   **Testing:** The project includes unit tests for the ViewModels, repository, and PagingSource.
*   **Dependency Management:** Dependencies are managed using the Gradle version catalog (`gradle/libs.versions.toml`).
*   **Feature Branches:** Each new feature will be developed in a new branch named "feature/feature_name".