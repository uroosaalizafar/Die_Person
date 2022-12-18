package com.uroosa.dieperson.models.response

data class UserResponse(
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val status: String
)