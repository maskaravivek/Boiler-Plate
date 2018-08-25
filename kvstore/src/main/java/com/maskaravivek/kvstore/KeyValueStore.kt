package com.maskaravivek.kvstore

interface KeyValueStore {
    fun getString(key: String): String

    fun getBoolean(key: String): Boolean

    fun getLong(key: String): Long

    fun getInt(key: String): Int

    fun putString(key: String, value: String)

    fun putBoolean(key: String, value: Boolean)

    fun putLong(key: String, value: Long)

    fun putInt(key: String, value: Int)

    operator fun contains(key: String): Boolean

    fun remove(key: String)

    fun clearAll()

    fun clearAllWithVersion()
}
