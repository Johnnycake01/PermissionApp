package com.example.customcontact.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.customcontact.R
import com.example.customcontact.dataClass.ContactItem
   const val REQUEST_CALL = 1
class DisplayContactInfo : AppCompatActivity() {
    private lateinit var phoneNumber:TextView
    private lateinit var profileName:TextView
    private lateinit var obj:ContactItem
    private lateinit var call:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_contact_info)
       phoneNumber= findViewById(R.id.mobileNumber)
        profileName = findViewById(R.id.profilePageName)
        call = findViewById(R.id.view2)

        phoneNumber.text = intent.getStringExtra("PHONE_NUMBER")
        profileName.text = intent.getStringExtra("NAME")

    }
    fun callNumber(view: View){
        makeCall()
    }
    private fun makeCall(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)

        }else{
            val dail = "tel:${phoneNumber.text}"
            val intent = Intent(Intent.ACTION_CALL, Uri.parse(dail))
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL){
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall()
            }else{
                Toast.makeText(this,"Contact Permission Denied",Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}