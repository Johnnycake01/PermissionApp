package com.example.contactapp.ui

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.adapter.ContactListAdapter
import com.example.contactapp.dataclasses.ContactItem
import com.example.contactapp.objects.Functions

//constant value to specify permission to read contact request code
const val READ_CONTACT = 1
class MainActivity : AppCompatActivity() {
    //variable declaration
    private lateinit var button: Button
    private lateinit var recyclerViewView:RecyclerView
    private lateinit var errorButton:TextView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //assigning views to variables
        recyclerViewView = findViewById(R.id.recycleViewView)
        errorButton = findViewById(R.id.textInput_error)
        button = findViewById(R.id.read_button)
        textView = findViewById(R.id.TextInBox)

        //layout manager for recycler view
        recyclerViewView.layoutManager = LinearLayoutManager(this)

        // function to read contact
        readContact()
        //button listener to read contact
        button.setOnClickListener {
            button.visibility = View.GONE //hide button
            errorButton.visibility = View.GONE //hide text
            readContact() //try to read contact
        }

    }


//funcyion yo read phone contact
    private fun readContact(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS), READ_CONTACT
            )

        } else {

           showContact()


        }

    }
//on permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CONTACT){
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               showContact()
            }else{
                button.visibility = View.VISIBLE
                errorButton.visibility = View.VISIBLE

            }
        }
    }

    //display contact
    private fun showContact(){
        //query phone contact list
        val contact = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        //check if contact is not empty
        if (contact != null) {
            while (contact.moveToNext()) {//continue looping through list of contact if it has an element
                //get contact name
                val name = contact.getString(
                    contact.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )
                //get phone numbar
                val number = contact.getString(
                    contact.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
                //get image if contact contain image
                val imageResourse = contact.getString(
                    contact.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                )
                //create an object of dataclass and pass to list for recycler view to display
                val obj = ContactItem("", null, name, number)
                if (imageResourse != null) {
                    obj.image = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        Uri.parse(imageResourse)
                    )
                    obj.contactSymbol = ""   //if it contain image dont display text
                } else {
                    obj.contactSymbol = Functions.getFirstAlphabet(name).toString()
                }

                Functions.contactList.add(obj)
            }
        }
        //call list and sort list of contacts gotten from phone database
        val recyclerContactAdapter =
            ContactListAdapter(Functions.contactList.sortedBy { it.contactName })

        recyclerViewView.adapter = recyclerContactAdapter
    }

}