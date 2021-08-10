package com.example.customcontact.objects

object FunctionsAndValidation {
    fun validateName(string:String):Boolean{
        if (string.isEmpty()) return false
        return true
    }

    fun joinName(name1:String, name2:String):String{
        return "$name1 $name2"
    }

    fun getFirstAlphabet(string: String):Char{
        for(i in string.indices) {
            if (string[i] != ' ') {
                return string[i].toUpperCase()
            }
        }
        return ' '
    }

}