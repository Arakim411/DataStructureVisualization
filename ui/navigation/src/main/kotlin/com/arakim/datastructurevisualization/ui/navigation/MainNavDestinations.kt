package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

enum class MainNavDestinations(val route: String) {
    BinarySearchTree("binarySearchTree"),
    Stack("stack"),
    Queue("queue"),
    LinkedList("linkedList"),
    HashTable("hashTable"),

    Favorite("favorite"),
    Delete("delete"),

}

@Composable
fun MainNavDestinations.toStringResources(): String = when (this) {
    MainNavDestinations.BinarySearchTree -> stringResource(id = R.string.destination_name_binary_search_tree)
    MainNavDestinations.Stack -> stringResource(id = R.string.destination_name_stack)
    MainNavDestinations.Queue -> stringResource(id = R.string.destination_name_queue)
    MainNavDestinations.LinkedList -> stringResource(id = R.string.destination_name_linked_list)
    MainNavDestinations.HashTable -> stringResource(id = R.string.destination_name_hash_table)
    MainNavDestinations.Favorite -> stringResource(id = R.string.destination_name_favorite)
    MainNavDestinations.Delete -> stringResource(id = R.string.destination_name_delete)
}