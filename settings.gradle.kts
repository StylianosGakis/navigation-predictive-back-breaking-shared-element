pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    maven(url = "https://androidx.dev/snapshots/builds/12208038/artifacts/repository")
    google()
    mavenCentral()
  }
}

rootProject.name = "predictive-navigation-repro"
include(":app")