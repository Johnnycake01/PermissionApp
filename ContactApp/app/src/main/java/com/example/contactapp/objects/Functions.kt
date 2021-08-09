package com.example.contactapp.objects

import com.example.contactapp.dataclasses.ContactItem

object Functions {
    var contactList: MutableList<ContactItem> = mutableListOf()
    fun getFirstAlphabet(string: String):Char{
        for(i in string.indices) {
            if (string[i] != ' ') {
                return string[i].toUpperCase()
            }
        }

        return ' '
    }
}