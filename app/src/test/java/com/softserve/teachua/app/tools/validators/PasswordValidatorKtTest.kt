package com.softserve.teachua.app.tools.validators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class PasswordValidatorKtTest{

    private val data = listOf(
            false to "admin@gmail.com",
            true to "Admin#1234"
    )

    @TestFactory
    fun test() = data.map{ (expected, input) ->
        DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
            assertEquals(expected, !input.isValidEmail())
        }
    }


}