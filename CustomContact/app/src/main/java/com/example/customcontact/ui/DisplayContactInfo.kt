package com.example.customcontact.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.customcontact.R
import com.example.customcontact.dataClass.ContactItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


const val REQUEST_CALL = 1
const val REQUEST_MESSAGE = 2

class DisplayContactInfo : AppCompatActivity() {
    //declaration of variables
    private lateinit var phoneNumber: TextView
    private lateinit var profileName: TextView
    private lateinit var deleteButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var updateButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_contact_info)

        //assign values to variables or find view link to variables
        phoneNumber = findViewById(R.id.mobileNumber)
        profileName = findViewById(R.id.profilePageName)
        deleteButton = findViewById(R.id.deleteButton)
        updateButton = findViewById(R.id.updateButton)

        database = FirebaseDatabase.getInstance().getReference("Contacts")
        phoneNumber.text = intent.getStringExtra("PHONE_NUMBER")
        profileName.text = intent.getStringExtra("NAME")

        deleteButton.setOnClickListener {
            deleteContact()
        }

        updateButton.setOnClickListener {
            editContactUpdate()
        }
    }
    //onclick event to share contact
    fun share(view: View) {
        shareContact()
    }
    //onclick event to message contact
    fun message(view: View) {
        messageContact()
    }
    //onclick event to callNumber
    fun callNumber(view: View) {
        makeCall()
    }

    //edit contact function uses intent to pass name(used as id in database) to
    // identify contact to be edited
    private fun editContactUpdate() {
        val fullName = profileName.text.trim().toString()
        val intent = Intent(this, UpdateContactReg::class.java)
        intent.putExtra("realUpdateName", fullName)
        startActivity(intent)
    }

    //delete contact function to delete contact from database
    private fun deleteContact() {
        val fullName = profileName.text.trim().toString()

        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {

                        database.child(fullName).setValue(
                            ContactItem(null, null, null)
                        )

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete contact?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()

    }

    //function to share contact using implicit intent
    private fun shareContact() {
        val intentSend = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${phoneNumber.text}")
        }
        startActivity(Intent.createChooser(intentSend, "Share using: "))
    }

    //function to message a contact, ask for permission to access message before performing
    // action
    private fun messageContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS), REQUEST_MESSAGE
            )

        } else {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data = Uri.parse("smsto:${phoneNumber.text}")
            }

            startActivity(Intent.createChooser(intent, "Share using: "))
        }
    }

    //function to call a contact, ask for permission to access message before performing
    // action
    private fun makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL
            )

        } else {
            val dail = "tel:${phoneNumber.text}"
            val intent = Intent(Intent.ACTION_CALL, Uri.parse(dail))
            startActivity(intent)
        }
    }

    //how permission is handled when request for permission is called
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                Toast.makeText(this, "Contact Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
            if (requestCode == REQUEST_MESSAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    messageContact()
                } else {
                    Toast.makeText(this, "Message Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }

            }


        }

    }

    //on backpressed go to main activity
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}