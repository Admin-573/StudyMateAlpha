package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdmin: Button
    private lateinit var btnOther: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdmin = findViewById(R.id.btnAdmin)
        btnOther = findViewById(R.id.btnOther)

        btnAdmin.setOnClickListener {
            requestRuntimePermission()
        }
        btnOther.setOnClickListener {
            startActivity(Intent(applicationContext,Faculty::class.java))
        }

        onBackPressedDispatcher.addCallback {  }
    }
    private fun requestRuntimePermission() {
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//        Toast.makeText(applicationContext,"Permission Granted !", Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext,Admin::class.java))
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
            materialAlertDialogBuilder.setMessage("This app require READ_IMAGES permission from particular feature to work as  excepted !")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("Ok"){
                        dialog,msg ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){
                        dialog,msg ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Permission Granted !", Toast.LENGTH_LONG).show()
            }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                materialAlertDialogBuilder.setMessage("This feature is unavailable because this feature permission that you have denied."+ "Please allow Image permission from setting to proceed further")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("Setting"){
                            dialog,msg ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package",packageName,null)
                        intent.data= uri
                        startActivity(intent)

                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"){
                            dialog,msg ->
                        dialog.dismiss()
                    }
                materialAlertDialogBuilder.create().show()
            }
        }
    }
}