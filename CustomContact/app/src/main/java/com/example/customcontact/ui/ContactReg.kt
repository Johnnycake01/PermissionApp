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

class ContactReg : AppCompatActivity() {
    //declaration of variables
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var database:DatabaseReference
    private lateinit var clickSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_reg)

        //assign values to variables or find view link to variables
       database = FirebaseDatabase.getInstance().getReference("Contacts")
        clickSave = findViewById(R.id.saveButtonClick)
        firstName = findViewById(R.id.editFirstName)
        lastName = findViewById(R.id.editLastName)
        phoneNumber = findViewById(R.id.editPhoneNumber)

        //save button
        clickSave.setOnClickListener {
            saveContact() //function to save contact
        }

    }

    //save contact function
    private fun saveContact() {

        //check if name is valid i.e not null
        if (FunctionsAndValidation.validateName(firstName.text.toString())
            || FunctionsAndValidation.validateName(lastName.text.toString())
            || FunctionsAndValidation.validateName(phoneNumber.text.toString())
        ) {
            val firstNameInput = firstName.text.trim().toString()
            val lastNameInput = lastName.text.trim().toString()
            val fullName = FunctionsAndValidation.joinName(firstNameInput,lastNameInput)
            val phoneNumberInput = phoneNumber.text.toString()

            database.child(fullName).setValue(
                ContactItem(FunctionsAndValidation.getFirstAlphabet(fullName).toString(),
                    fullName,phoneNumberInput))

            val intent = Intent(this, DisplayContactInfo::class.java)
            intent.putExtra("NAME", fullName)
            intent.putExtra("PHONE_NUMBER", phoneNumber.text.toString())
            startActivity(intent)
            Toast.makeText(
                this, "contact saved",
                Toast.LENGTH_SHORT
            ).show()

        } else { //if null do this
            Toast.makeText(
                this, "contact information is null and cannot be saved",
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        }
    }


}