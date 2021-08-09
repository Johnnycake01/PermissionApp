package com.example.customcontact.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.customcontact.R
import com.example.customcontact.`interface`.OnContactClicked
import com.example.customcontact.adapter.ContactListAdapter
import com.example.customcontact.dataClass.ContactItem
import com.example.customcontact.objects.MutableListOfContact
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), OnContactClicked {
    private lateinit var sortedList: List<ContactItem>
    private lateinit var rvView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var contactListSingle: MutableList<ContactItem>
    private lateinit var recyclerContactAdapter:ContactListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvView = findViewById(R.id.recycleViewView)

        contactListSingle = mutableListOf()
        getContactInformationFromFirebase()



//                        if (!MutableListOfContact.contactList.contains(fullName)) {
//                            MutableListOfContact.contactList.add(
//                                ContactItem(
//                                    fullSymbol.toString(), fullName.toString(),
//                                    fullNumber.toString()
//                                )
//                            )
//                        }
//


//        }
//        database.addValueEventListener(getDataFromFirebase)
//        database.addListenerForSingleValueEvent(getDataFromFirebase)

//        sortedList = contactListSingle.sortedBy { it.contactName }

        rvView.layoutManager = LinearLayoutManager(this)


    }

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


    fun reg(view: View) {
        val intent = Intent(this, ContactReg::class.java)
        startActivity(intent)
    }

    override fun clickEvent(position: Int) {
        val intent = Intent(this, DisplayContactInfo::class.java)
        intent.putExtra("NAME", contactListSingle[position].contactName)
        intent.putExtra("PHONE_NUMBER", contactListSingle[position].phoneNumber)
        startActivity(intent)
    }
}




//database.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (i in snapshot.children) {
//                        val fullName = i.child("contactName").getValue()
//                        val fullSymbol = i.child("contactSymbol").getValue()
//                        val fullNumber = i.child("phoneNumber").getValue()
//
//                        contactListSingle.add(
//                            ContactItem(
//                                fullSymbol.toString(), fullName.toString(),
//                                fullNumber.toString()
//                            )
//                        )
//
//                    }
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.also {
//            it.putString("language", text.text.toString())
//            it.putInt("countSave", count)
//            it.putString("textValue", showHide.text.toString())
//            it.putInt("image visibility state", imageView.visibility)
//        }
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        val returnLanguage = savedInstanceState.getString("language")
//        returnLanguage.also { text.text = it }
//        val returnCount = savedInstanceState.getInt("countSave")
//        count = returnCount
//
//        "$count".also {
//            counter.text = it
//        }
//        val returnState = savedInstanceState.getString("textValue")
//        showHide.text = returnState
//        val imageVisibility = savedInstanceState.getInt("image visibility state")
//        imageView.visibility = imageVisibility
//    }


