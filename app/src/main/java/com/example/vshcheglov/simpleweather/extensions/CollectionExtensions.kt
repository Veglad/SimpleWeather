package com.example.vshcheglov.simpleweather.extensions

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map { it.key to it.value!! }.toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?) : R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }

    throw NoSuchElementException("No element matching predicate was found.")
}