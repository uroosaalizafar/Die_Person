package com.uroosa.dieperson.models.request

data class UserRequest(
    val email: String,
    val gender: String,
    val name: String,
    val status: String
)