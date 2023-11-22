package com.arakim.datastructurevisualization.ui.navigation.destination

import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.BinarySearchTreeDestination.Arguments.Id

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

    class BinarySearchTreeDestination(id: Int) : MainDestination {
        override val navigateRoute: String = "$BaseRoute/$id"

        object Arguments {
            const val Id = "id"
        }

        companion object {
            private val BaseRoute = "binary_search_tree"
            val Route = "$BaseRoute/{$Id}"
        }
    }

}