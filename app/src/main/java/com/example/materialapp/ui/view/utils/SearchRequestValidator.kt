package com.example.materialapp.ui.view.utils

class SearchRequestValidator {
    companion object {
        fun isValid(request: String): Boolean {
            val regex = "[а-яА-Яa-zA-Z0-9' \",.?!;:\$_-]{3,100}$".toRegex()
            return request.isNotBlank() && request.matches(regex)
        }
    }
}