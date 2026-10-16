package com.example.ptp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ptp.composition.Peer
import com.example.ptp.composition.PeerAdapter
import com.example.ptp.helper.P2pHelper
import com.example.ptp.helper.PermissionHelper
import com.example.ptp.`object`.P2pReceiver

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    private val permissionHelper : PermissionHelper = PermissionHelper(this)
    private val p2pHelper : P2pHelper = P2pHelper(this)
    private lateinit var p2pReceiver : P2pReceiver

    private val peerList = ArrayList<Peer>()

    @SuppressLint("MissingPermission", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p2pHelper.init()
        p2pReceiver = P2pReceiver(p2pHelper.manager!!,p2pHelper.channel!!,this)

        val btnSearch = findViewById<Button>(R.id.search)
        btnSearch.setOnClickListener {
            permissionHelper.ensureP2pPermission(onGranted = {
                p2pHelper.discoverPeers()
            })
        }

        val btnShow = findViewById<Button>(R.id.show)
        btnShow.setOnClickListener {
            MyApp.instance.p2pContainer.store.map().forEach { (deviceName, deviceAddress) ->
                Log.d("CatCat", "设备: $deviceName / $deviceAddress")
                addPeerList(deviceName,deviceAddress)
            }
            val recyclerView = findViewById<RecyclerView>(R.id.PeerView)
            recyclerView.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(this@HomeActivity,R.drawable.item_divider)!!)
                }
            )
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            val adapter = PeerAdapter(peerList)
            recyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        p2pReceiver.also { p2pReceiver -> registerReceiver(p2pReceiver,p2pReceiver.intentFilter,RECEIVER_EXPORTED) }
    }

    override fun onPause() {
        super.onPause()
        p2pReceiver.also { p2pReceiver -> unregisterReceiver(p2pReceiver) }
    }

    private fun addPeerList(name: String, address: String){
        peerList.add(Peer(name,address))
    }

}