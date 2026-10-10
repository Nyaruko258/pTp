import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.ptp.MainActivity

class WiFiDirectBroadcastReceiver (
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: MainActivity
): BroadcastReceiver(){

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES])
    override fun onReceive(context: Context, intent: Intent) {
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
                        Log.d("CatCat", "设备: ${device.deviceName} / ${device.deviceAddress}")
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION->{}
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION->{}
        }
    }
}