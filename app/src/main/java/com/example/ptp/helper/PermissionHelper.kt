package com.example.ptp.helper

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(private  val activity: AppCompatActivity){
    private var pendingOnGranted: (()->Unit)? = null
    private var pendingOnDenied : (()->Unit)? = null
    private val launcher: ActivityResultLauncher<String> by lazy{
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ granted->
            if(granted){
                pendingOnGranted?.invoke()
            }else{
                pendingOnDenied?.invoke()
            }
            clearPending()
        }
    }
    private fun clearPending(){
        pendingOnGranted = null
        pendingOnDenied  = null
    }
    private fun isGranted(): Boolean{
        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.NEARBY_WIFI_DEVICES
        ) == PackageManager.PERMISSION_GRANTED
        return isGranted
    }
    private fun shouldShowRationale(): Boolean{
        val isRationaleNeeded = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.NEARBY_WIFI_DEVICES
        )
        return isRationaleNeeded
    }
    fun ensureP2pPermission(
        onGranted: ()->Unit,
        onDenied : ()->Unit={}
    ){
        pendingOnGranted = onGranted
        pendingOnDenied  = onDenied

        when{
            isGranted()->{
                clearPending()
                onGranted()
            }
            shouldShowRationale()->{
                showRationaleDialog()
            }
            else->{
                launcher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
        }
    }
    private fun showRationaleDialog(){
        AlertDialog.Builder(activity)
            .setTitle("需要权限")
            .setMessage("Wi-Fi P2P 需要附近设备权限才能发现和连接设备。")
            .setCancelable(false)
            .setPositiveButton("好的"){_,_->
                launcher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
            .setNegativeButton("取消"){_,_->
                pendingOnDenied?.invoke()
                clearPending()
            }
            .show()
    }
}