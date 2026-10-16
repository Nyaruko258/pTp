package com.example.ptp.`object`

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.example.ptp.HomeActivity
import com.example.ptp.MyApp

class P2pReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: HomeActivity
): BroadcastReceiver() {
    val intentFilter = IntentFilter().apply{
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES])
    override fun onReceive(context: Context, intent: Intent){
        val action: String? = intent.action
        when(action){
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION->{
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1)
                when(state){
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED->{}
                    else -> {}
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION->{
                manager.requestPeers(channel) { peers: WifiP2pDeviceList?->
                    peers?.deviceList?.forEach { device ->
                        MyApp.instance.p2pContainer.store.push(device.deviceName,device.deviceAddress)
                    }
                    Toast.makeText(context,"Find Ok", Toast.LENGTH_SHORT).show()
                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION->{}
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION->{}
        }
    }
}