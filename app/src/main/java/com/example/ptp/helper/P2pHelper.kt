package com.example.ptp.helper

import android.Manifest
import android.content.Context
import android.content.Context.WIFI_P2P_SERVICE
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import androidx.annotation.RequiresPermission

class P2pHelper(private val context: Context) {
    val manager : WifiP2pManager? by lazy{
        context.getSystemService(WIFI_P2P_SERVICE) as? WifiP2pManager }
    var channel : WifiP2pManager.Channel? = null
    fun init() {
        channel = manager?.initialize(context, context.mainLooper, null)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES])
    fun discoverPeers(){
        manager?.discoverPeers(channel, object:WifiP2pManager.ActionListener{
            override fun onSuccess() {
                Log.d("CatCat","P2P OK")
            }

            override fun onFailure(reason: Int) {
                Log.d("CatCat","P2P Failed: $reason")
            }
        })
    }
}