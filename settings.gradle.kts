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
include(":data:database")
include(":data:repository")
include(":data:repository:data-structure")
include(":data:repository:data-structure:local-data-source")
include(":data:repository:visualization-set-up")
include(":data:repository:visualization-set-up:local-data-source")
include(":domain")
include(":domain:data-structure")
include(":domain:visualization-set-up")
include(":kotlin-util")
include(":test-data-generator")
include(":test-util")
include(":ui")
include(":ui:common")
include(":ui:common:generic-picker")
include(":ui:mvi")
include(":ui:navigation")
include(":ui:navigation:destination")
include(":ui:navigation:ui-controller")
include(":ui:screens:binary-search-tree")
include(":ui:screens:choose-data-structure")
include(":ui:screens:deleted-data-structures")
include(":ui:screens:hash-map")
include(":ui:util")
include(":ui:visualization-builder")
include(":ui:visualization-builder:set-up-picker")
include(":ui:visualization-builder:visualization-core")
