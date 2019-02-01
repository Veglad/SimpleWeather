package com.example.vshcheglov.simpleweather.extensions

import android.content.Context
import kotlin.reflect.KProperty

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun longPreference(context: Context, name: String,
                       default: Long) = LongPreference(context, name, default)
}

class NotNullSingleValueVar<T> {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            value ?: throw IllegalStateException("${property.name} not initialized")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if(this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }
}

class LongPreference(private val context: Context, val name: String, private val default: Long) {

    private val preference by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return preference.getLong(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        preference.edit().putLong(name, value).apply()
    }
}
