package com.softserve.teachua.app.tools.validators

import java.util.regex.Matcher
import java.util.regex.Pattern


private const val passwordLengthPattern = "^.{8,20}$"
private const val caseSensitivePattern = "^(?=.*[a-z])(?=.*[A-Z]).{2,}\$"
private const val specialSymbolsPattern = "^(?=.*[@#\$%^&+=]).{1,}\$"
private const val spacePattern = "^([^\\s]).{1,}$"
private const val emailPattern = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

private fun isValid(string: String, validatorContainer: List<ValidatorContainer>) : ErrorContainer{
    val errors = ArrayList<PasswordError>()
    var pattern : Pattern
    var matcher : Matcher
    for (validator in validatorContainer){
        pattern = Pattern.compile(validator.regex)
        matcher = pattern.matcher(string)
        if (!matcher.matches()){
            errors.add(validator.error)
        }
    }
    return ErrorContainer(errors)
}

fun String.isValidPassword() : ErrorContainer{
    return isValid(this, ValidatorContainer.passwordValidators())
}

fun String.isValidEmail() : ErrorContainer{
    return isValid(this, ValidatorContainer.emailValidators())
}

class ErrorContainer(private val errors:List<PasswordError>){
    operator fun not() : Boolean{
        return errors.isNotEmpty()
    }

    fun isValid():Boolean{
        return errors.isEmpty()
    }

    fun getErrors() : List<PasswordError> {
        return errors
    }
}

data class ValidatorContainer(
    val error: PasswordError,
    val regex: String
        ) {
    companion object{
        fun passwordValidators() : List<ValidatorContainer>{
            return listOf(lengthValidator(), caseSensitiveValidator(), specialSymbolsValidator(), noSpacePattern())
        }

        fun emailValidators() : List<ValidatorContainer>{
            return listOf(emailValidator())
        }

        private fun emailValidator():ValidatorContainer{
            return ValidatorContainer(PasswordError.EMAIL_ERROR, emailPattern)
        }

        private fun lengthValidator(): ValidatorContainer {
            return ValidatorContainer(PasswordError.LENGTH_ERROR, passwordLengthPattern)
        }

        private fun caseSensitiveValidator() : ValidatorContainer {
            return ValidatorContainer(PasswordError.CASE_SENSITIVE_ERROR, caseSensitivePattern)
        }

        private fun specialSymbolsValidator() : ValidatorContainer{
            return ValidatorContainer(PasswordError.SPECIAL_SYMBOL_ERROR, specialSymbolsPattern)
        }

        private fun noSpacePattern() : ValidatorContainer{
            return ValidatorContainer(PasswordError.SPACE_ERROR, spacePattern)
        }


    }
}

enum class PasswordError{
    LENGTH_ERROR, CASE_SENSITIVE_ERROR, SPECIAL_SYMBOL_ERROR, SPACE_ERROR, EMAIL_ERROR
}