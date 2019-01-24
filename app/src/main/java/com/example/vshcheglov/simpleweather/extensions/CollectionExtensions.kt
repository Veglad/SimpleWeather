package com.example.vshcheglov.simpleweather.extensions

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map { it.key to it.value!! }.toTypedArray()