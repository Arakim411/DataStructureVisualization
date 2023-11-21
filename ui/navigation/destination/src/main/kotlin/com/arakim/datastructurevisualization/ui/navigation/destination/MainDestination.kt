package com.arakim.datastructurevisualization.ui.navigation.destination

sealed interface MainDestination {
    val navigateRoute: String

    object ChooseDataStructureDestination : MainDestination {
        const val Route = "choose_data_structure"
        override val navigateRoute: String = Route
    }

    object DeletedDataStructuresDestination : MainDestination {
        const val Route = "deleted_data_structures"
        override val navigateRoute: String = Route
    }

}