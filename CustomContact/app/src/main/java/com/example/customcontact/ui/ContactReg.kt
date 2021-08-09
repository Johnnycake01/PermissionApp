package com.example.customcontact.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.customcontact.R
import com.example.customcontact.dataClass.ContactItem
import com.example.customcontact.objects.functionsAndValidation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ContactReg : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var database:DatabaseReference
    private lateinit var clickSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_reg)
       database = FirebaseDatabase.getInstance().getReference("Contacts")
        clickSave = findViewById(R.id.saveButtonClick)
        firstName = findViewById(R.id.editFirstName)
        lastName = findViewById(R.id.editLastName)
        phoneNumber = findViewById(R.id.editPhoneNumber)

        clickSave.setOnClickListener {
            if (functionsAndValidation.validateName(firstName.text.toString())
                || functionsAndValidation.validateName(lastName.text.toString())
                || functionsAndValidation.validateName(phoneNumber.text.toString())
            ) {
                val firstNameInput = firstName.text.trim().toString()
                val lastNameInput = lastName.text.trim().toString()
                val fullName = functionsAndValidation.joinName(firstNameInput,lastNameInput)
                val phoneNumberInput = phoneNumber.text.toString()

                database.child(fullName).setValue(
                    ContactItem(functionsAndValidation.getFirstAlphabet(fullName).toString(),
                    fullName,phoneNumberInput))

                    val intent = Intent(this, DisplayContactInfo::class.java)
                    intent.putExtra("NAME", fullName)
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


}