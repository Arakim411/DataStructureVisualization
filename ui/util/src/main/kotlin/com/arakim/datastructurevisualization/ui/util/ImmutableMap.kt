package com.arakim.datastructurevisualization.ui.util

import javax.annotation.concurrent.Immutable

@Immutable
class ImmutableMap<K, out V>(val value: Map<K, V>) : Map<K, V> by value {

    override fun equals(other: Any?): Boolean = when (other) {
        this -> false
        !is ImmutableMap<*, *> -> false
        else -> value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}


inline fun <K, V> immutableMapOf(): ImmutableMap<K, V> = ImmutableMap(emptyMap())

inline fun <K, V> immutableMapOf(vararg pairs: Pair<K, V>): ImmutableMap<K, V> = ImmutableMap(pairs.toMap())

inline fun <K, V, R> ImmutableMap<K, V>.map(transform: (Map.Entry<K, V>) -> R): ImmutableList<R> {
    return ImmutableList(value.map { transform(it) })
}

inline fun <K, V> Map<K, V>.toImmutableMap(): ImmutableMap<K, V> = ImmutableMap(this)