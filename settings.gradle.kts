pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DataStructureVisualization"
include(":app")
include(":ui")
include(":ui:common")
include(":ui:navigation")
include(":ui:navigation:ui-controller")
include(":ui:screens:binary-search-tree")
include(":ui:util")
include(":ui:visualization-builder")
include(":ui:visualization-builder:visualization-engine")
