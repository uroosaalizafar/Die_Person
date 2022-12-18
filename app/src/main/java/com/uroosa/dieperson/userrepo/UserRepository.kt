package com.uroosa.dieperson.userrepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.uroosa.dieperson.api.UserApi
import com.uroosa.dieperson.models.request.UserRequest
import com.uroosa.dieperson.models.response.UserResponse
import com.uroosa.dieperson.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _usersLiveData = MutableLiveData<NetworkResult<List<UserResponse>>>()
    val userLiveData get() = _usersLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createUser(userRequest: UserRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = userApi.createUser(userRequest)
        handleResponse(response, "User Created")
    }

    suspend fun getUsers() {
        _usersLiveData.postValue(NetworkResult.Loading())
        val response = userApi.getUser()
        if (response.isSuccessful && response.body() != null) {
            Log.e("success", ""+response.body())
            _usersLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            Log.e("ERROR", ""+response.errorBody())
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _usersLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _usersLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun updateUser(id: String, userRequest: UserRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = userApi.updateUser(id, userRequest)
        Log.e("UserApi message ", response.message())
        Log.e("User id ", id)
        Log.e("User name ",userRequest.name)
        Log.e("User email ",userRequest.email)
        Log.e("User gender ",userRequest.gender)
        handleResponse(response, "User Updated")
    }

    suspend fun deleteUser(userId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = userApi.deleteUser(userId)
        handleResponse(response, "User Deleted")
    }

    private fun handleResponse(response: Response<UserResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            Log.e("UserApi isSuccessful ", response.message())
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
        } else {
            Log.e("UserApi unSuccessful ", response.message())
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }

}