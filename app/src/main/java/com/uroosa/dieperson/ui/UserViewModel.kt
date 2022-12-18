package com.uroosa.dieperson.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uroosa.dieperson.models.request.UserRequest
import com.uroosa.dieperson.userrepo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val usersLiveData get() = userRepository.userLiveData
    val statusLiveData get() = userRepository.statusLiveData

    fun createUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.createUser(userRequest)
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getUsers()
        }
    }

    fun updateUser(id: String, userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.updateUser(id, userRequest)
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            userRepository.deleteUser(userId)
        }
    }




}