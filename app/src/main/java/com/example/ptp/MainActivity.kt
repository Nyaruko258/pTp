package com.example.ptp

import PermissionHelper
import WiFiDirectBroadcastReceiver
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val tag = "CatCat"
    private val manager : WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE){
        getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager? }
    private var channel : WifiP2pManager.Channel? = null
    private var receiver : BroadcastReceiver? = null

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}


    private var permissionHelper: PermissionHelper? = null

    val intentFilter = IntentFilter().apply{
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        channel = manager?.initialize(this,mainLooper,null)
        channel?.also { channel -> receiver = WiFiDirectBroadcastReceiver(manager!!, channel, this) }

        permissionHelper?.checkPermission(this)

        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d(tag,"Discover Ok")
            }
            override fun onFailure(reasonCode: Int) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        receiver?.also { receiver -> registerReceiver(receiver,intentFilter,RECEIVER_EXPORTED) }
    }

    override fun onPause() {
        super.onPause()
        receiver?.also { receiver -> unregisterReceiver(receiver) }
    }

}