package com.example.contactapp.dataclasses

import android.graphics.Bitmap

data class ContactItem(
    var contactSymbol:String? = null,
    var image:Bitmap?=null,
    var contactName:String? = null,
    var phoneNumber:String? = null
)
