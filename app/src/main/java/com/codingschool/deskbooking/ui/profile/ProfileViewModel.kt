package com.codingschool.deskbooking.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.MainActivity
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.ProfileRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository, private val loginRepository: LoginRepository) : ViewModel() {
    val userProfile = MutableLiveData<ProfileResponse?>()
    val isAdmin = MutableLiveData<Boolean?>()
    val isLoggedOut = MutableLiveData<Boolean>()

    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val result = profileRepository.getUserProfile()
                userProfile.value = result.getOrThrow()  // Verwenden Sie getOrThrow, um die erfolgreiche Antwort zu erhalten oder eine Ausnahme zu werfen
                isAdmin.value = result.getOrThrow().isAdmin
            } catch (exception: Exception) {
                Log.e("ProfileViewModel", "Fehler beim Abrufen des Profils", exception)
                userProfile.value = null  // Setzen Sie userProfile auf null, wenn ein Fehler auftritt
                isAdmin.value = null
            }
        }
    }

    fun logoutUser() {
        // Löschen der Login-Tokens
        loginRepository.clearLoginTokens()
        RetrofitClient.authToken = null

        // Löschen des App-Caches
        loginRepository.clearApplicationCache()

        // Zurücksetzen der Profildaten
        resetProfile()

        // Benachrichtigen, dass der Benutzer abgemeldet wurde
        isLoggedOut.postValue(true)
    }

    private fun resetProfile() {
        userProfile.postValue(null)
        isAdmin.postValue(null)
        // Benachrichtigen, dass der Benutzer abgemeldet wurde (optional)
        // Diese LiveData könnte von MainActivity beobachtet werden, um UI zu aktualisieren
        isLoggedOut.postValue(true)
    }


}
