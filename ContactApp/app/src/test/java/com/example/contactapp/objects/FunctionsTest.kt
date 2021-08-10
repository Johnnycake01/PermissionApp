package com.example.contactapp.objects

import org.junit.Test

import org.junit.Assert.*

class FunctionsTest {
    private lateinit var firstName:String
    private lateinit var lastName:String

    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter_returnChar() {
        firstName = "Johnson"
        val result = Functions.getFirstAlphabet(firstName)
        assertEquals('J',result)

    }
    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter2_returnChar() {
        firstName = ""
        val result = Functions.getFirstAlphabet(firstName)
        assertEquals(' ',result)

    }
    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter3_returnChar() {
        firstName = "johnson"
        val result = Functions.getFirstAlphabet(firstName)
        assertEquals('J',result)

    }
}