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

include(":android-util")
include(":app")
include(":data")
include(":data:repository")
include(":data:repository:data-structure")
include(":data:repository:data-structure:local-data-source")
include(":domain")
include(":domain:data-structure")
include(":kotlin-util")
include(":ui")
include(":ui:common")
include(":ui:common:generic-picker")
include(":ui:mvi")
include(":ui:navigation")
include(":ui:navigation:ui-controller")
include(":ui:screens:binary-search-tree")
include(":ui:screens:choose-data-structure")
include(":ui:util")
include(":ui:visualization-builder")
include(":ui:visualization-builder:set-up-picker")
include(":ui:visualization-builder:visualization-core")
