package com.example.customcontact.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.customcontact.R
import com.example.customcontact.dataClass.ContactItem
import com.example.customcontact.objects.FunctionsAndValidation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateContactReg : AppCompatActivity() {
    //declaration of variables
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var database: DatabaseReference
    private lateinit var clickUpdate: Button
    private lateinit var fromIntentFullName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact_reg)
        //assign values to variables or find view link to variables
        database = FirebaseDatabase.getInstance().getReference("Contacts")
        clickUpdate = findViewById(R.id.updateButtonClick)
        firstName = findViewById(R.id.updateFirstName)
        lastName = findViewById(R.id.updateLastName)
        phoneNumber = findViewById(R.id.updatePhoneNumber)

        fromIntentFullName = intent.getStringArrayExtra("realUpdateName").toString()


        clickUpdate.setOnClickListener {
         updateContactInfo()

     }
    }

    //function to edit content of database
    private fun updateContactInfo() {
        if (FunctionsAndValidation.validateName(firstName.text.toString())
            || FunctionsAndValidation.validateName(lastName.text.toString())
            || FunctionsAndValidation.validateName(phoneNumber.text.toString())
        ) {
            val firstNameInput = firstName.text.trim().toString()
            val lastNameInput = lastName.text.trim().toString()
            val fullName = fromIntentFullName
            val updatedName = FunctionsAndValidation.joinName(firstNameInput,lastNameInput)
            val phoneNumberInput = phoneNumber.text.toString()

            database.child(fullName).setValue(
                ContactItem(
                    FunctionsAndValidation.getFirstAlphabet(updatedName).toString(),
                    updatedName,phoneNumberInput)
            )

            val intent = Intent(this, DisplayContactInfo::class.java)
            intent.putExtra("NAME",  updatedName)
            intent.putExtra("PHONE_NUMBER", phoneNumber.text.toString())
            startActivity(intent)
            Toast.makeText(
                this, "contact saved",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            Toast.makeText(
                this, "contact information is null and cannot be saved",
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        }
    }
}