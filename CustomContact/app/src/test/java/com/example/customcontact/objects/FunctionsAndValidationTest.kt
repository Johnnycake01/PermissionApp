package com.example.customcontact.objects

import org.junit.Test

import org.junit.Assert.*

class FunctionsAndValidationTest {
    private lateinit var firstName:String
    private lateinit var lastName:String

    @Test
    fun validateName_shouldReturnTrueIfNameIsValid_returnTrue() {
        firstName = "Johnson"
        val result = FunctionsAndValidation.validateName(firstName)
        assertTrue(result)
    }
    @Test
    fun validateName_shouldReturnTrueIfNameIsValid2_returnTrue() {
        firstName = "3hdjksn"
        val result = FunctionsAndValidation.validateName(firstName)
        assertTrue(result)
    }
    @Test
    fun validateName_shouldReturnTrueIfNameIsValid_returnFalse() {
        firstName = ""
        val result = FunctionsAndValidation.validateName(firstName)
        assertFalse(result)
    }

    @Test
    fun joinName_shouldReturnCombinationOfTwoStringWithSpaceInBetween_returnString(){
        firstName ="Oyesina"
        lastName = "Johnson"
        val result = FunctionsAndValidation.joinName(firstName,lastName)
        assertEquals("Oyesina Johnson",result)
    }

    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter_returnChar() {
        firstName = "Johnson"
        val result = FunctionsAndValidation.getFirstAlphabet(firstName)
        assertEquals('J',result)

    }
    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter2_returnChar() {
        firstName = ""
        val result = FunctionsAndValidation.getFirstAlphabet(firstName)
        assertEquals(' ',result)

    }
    @Test
    fun getFirstAlphabet_shouldReturnFirstLetterOfAnyStringInCapitalLetter3_returnChar() {
        firstName = "johnson"
        val result = FunctionsAndValidation.getFirstAlphabet(firstName)
        assertEquals('J',result)

    }
}