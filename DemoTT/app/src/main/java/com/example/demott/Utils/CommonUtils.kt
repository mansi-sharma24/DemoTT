package com.example.demott.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demott.R
import com.example.demott.view.activity.HomeActivity

open class CommonUtils {

    companion object{

        fun xToast(context : Context,message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun requestPermission(permission: Array<String> , requestCode : Int ,  activity: Activity){
            ActivityCompat.requestPermissions(
                activity,
                permission,
                requestCode
            )
        }

        fun  checkPermission(permissionName : String, activity: Activity) : Boolean{
            var result = ContextCompat.checkSelfPermission(
                activity,
                permissionName
            )
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun xActivity(current: Activity, destination: Class<HomeActivity>){
            val intent = Intent(current,destination::class.java)
            current.finish()
            current.startActivity(intent)
        }
        var alertDialog: AlertDialog? = null

        fun showDialog(activity: Activity) {
            dismissDialog()
            var alert = AlertDialog.Builder(activity)
            var mview: View =
                activity.layoutInflater.inflate(R.layout.dialog_progress, null)
            alert.setView(mview)
            alertDialog = alert.create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
            alertDialog?.setCanceledOnTouchOutside(false)
        }

        fun dismissDialog() {
            if (alertDialog != null) {
                alertDialog?.dismiss()
            }
        }

        fun getUserId(): String? {
            return App.appPreference1.getString(Constant.AUTH_TOKEN)
        }

//        fun hideBottomNavigation(activity: Activity, b: Boolean) {
//            activity.findViewById<BottomNavigationView>(R.id.bottomNavigation).visibility =
//                View.GONE
//        }
    }

}