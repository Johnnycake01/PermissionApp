package com.example.customcontact.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.customcontact.R
import com.example.customcontact.`interface`.OnContactClicked
import com.example.customcontact.adapter.ContactListAdapter
import com.example.customcontact.dataClass.ContactItem
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), OnContactClicked {
    //declaration of variables
    private lateinit var sortedList: List<ContactItem>
    private lateinit var rvView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var contactListSingle: MutableList<ContactItem>
    private lateinit var recyclerContactAdapter:ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvView = findViewById(R.id.recycleViewView)

        //created a new list to store data gotten from database
        contactListSingle = mutableListOf()

        //function to get information from firebase
        getContactInformationFromFirebase()

        //recycler view layout manager
        rvView.layoutManager = LinearLayoutManager(this)

    }
//get contact from firebase and insert in a list for the recycler view to show
    private fun getContactInformationFromFirebase() {
        database = FirebaseDatabase.getInstance().getReference("Contacts")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val fullName = i.child("contactName").getValue()
                        val fullSymbol = i.child("contactSymbol").getValue()
                        val fullNumber = i.child("phoneNumber").getValue()

                        contactListSingle.add(
                                ContactItem(
                                    fullSymbol.toString(), fullName.toString(),
                                    fullNumber.toString()
                                )
                            )

                    }
                    sortedList = contactListSingle.sortedBy { it.contactName }
                    recyclerContactAdapter =
                        ContactListAdapter(sortedList, this@MainActivity)
                    rvView.adapter = recyclerContactAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //onclick of fab button go to register contact page
    fun reg(view: View) {
        val intent = Intent(this, ContactReg::class.java)
        startActivity(intent)
    }

    // onclick of recycler view item display contact profile
    override fun clickEvent(position: Int) {
        val intent = Intent(this, DisplayContactInfo::class.java)
        intent.putExtra("NAME", sortedList[position].contactName)
        intent.putExtra("PHONE_NUMBER", sortedList[position].phoneNumber)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}