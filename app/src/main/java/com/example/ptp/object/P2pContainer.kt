package com.example.ptp.`object`

enum class PeerMapState {
    Uninitialized,
    Success,

}

class P2pContainer {
    val store : PeerStore = PeerStore()
}

class PeerStore{
    var storeState : PeerMapState = PeerMapState.Success
    var store : MutableMap<String, String> = mutableMapOf()

    fun push(k: String, v: String)
    {
        store[k] = v
    }

    fun pull(k : String): String?
    {
        return store[k]
    }

    fun allKeys(): List<String>
    {
        return store.keys.toList()
    }

    fun allValues(): List<String>
    {
        return store.values.toList()
    }

    fun map(): Map<String, String>
    {
        return store.toMap()
    }
}