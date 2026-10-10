import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ptp.MainActivity

class PermissionHelper(private val requestPermissionLauncher: ActivityResultLauncher<String>){
    fun checkPermission(activity: MainActivity){

        when{
            ContextCompat.checkSelfPermission(activity, Manifest.permission.NEARBY_WIFI_DEVICES) == PackageManager.PERMISSION_GRANTED->{}
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.NEARBY_WIFI_DEVICES)->{}
            else->{ requestPermissionLauncher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)}
        }
    }
}

