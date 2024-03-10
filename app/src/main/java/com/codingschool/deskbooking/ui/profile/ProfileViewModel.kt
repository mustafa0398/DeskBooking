package com.codingschool.deskbooking.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.data.model.dto.user.UserUpdate
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.ProfileRepository
import com.codingschool.deskbooking.data.repository.UserUpdateRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository,
    private val userUpdateRepository: UserUpdateRepository
) : ViewModel() {
    val userProfile = MutableLiveData<ProfileResponse?>()
    val isAdmin = MutableLiveData<Boolean?>()
    val isLoggedOut = MutableLiveData<Boolean>()

    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val result = profileRepository.getUserProfile()
                userProfile.value = result.getOrThrow()
                isAdmin.value = result.getOrThrow().isAdmin
            } catch (exception: Exception) {
                Log.e("ProfileViewModel", "Fehler beim Abrufen des Profils", exception)
                userProfile.value = null
                isAdmin.value = null
            }
        }
    }

    fun logoutUser() {
        loginRepository.clearLoginTokens()
        RetrofitClient.authToken = null
        loginRepository.clearApplicationCache()
        resetProfile()
        isLoggedOut.postValue(true)
    }

    private fun resetProfile() {
        userProfile.postValue(null)
        isAdmin.postValue(null)
        isLoggedOut.postValue(true)
    }

    suspend fun updateUser(id: String, userUpdate: UserUpdate): Result<UserUpdate> {
        return userUpdateRepository.updateUser(id, userUpdate)
    }


}
