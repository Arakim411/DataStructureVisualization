package com.arakim.datastructurevisualization.ui.util

import androidx.compose.runtime.Immutable

@Immutable
class ImmutableList<out T>(val value: List<T>) : List<T> by value {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is ImmutableList<*>) return false
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}

inline fun <T> immutableListOf(): ImmutableList<T> =
    ImmutableList(emptyList())

inline fun <T> immutableListOf(vararg elements: T): ImmutableList<T> =
    ImmutableList(if (elements.isNotEmpty()) elements.asList() else emptyList())

inline fun <T> immutableListOf(list: List<T>): ImmutableList<T> =
    ImmutableList(list.ifEmpty { emptyList() })

inline fun <T, R> ImmutableList<T>.map(transform: (T) -> R): ImmutableList<R> {
    return ImmutableList(value.map { transform(it) })
}

inline fun <T, R> Iterable<T>.mapToImmutable(transform: (T) -> R): ImmutableList<R> {
    return ImmutableList(this.map { transform(it) })
}