package com.maskaravivek.kvstore

interface KeyValueCollectionStore : KeyValueStore {
    fun putSet(key: String, s: Set<String>)

    fun getSet(key: String): Set<String>

    fun putMap(key: String, m: Map<String, String>)

    fun getMap(key: String): Map<String, String>
}
