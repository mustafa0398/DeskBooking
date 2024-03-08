package com.codingschool.deskbooking.ui.administration

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRequestUpdate
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponseUpdate
import com.codingschool.deskbooking.data.repository.CommentRepository
import com.codingschool.deskbooking.data.repository.FixDeskRequestRepository
import kotlinx.coroutines.launch


class AdminViewModel(private val commentRepository: CommentRepository, private val fixDeskRequestRepository: FixDeskRequestRepository) : ViewModel() {
    val comments = MutableLiveData<Result<List<CommentResponse>>>()
    val fixDeskRequests = MutableLiveData<Result<List<FixDeskResponse>>>()
    val updateResult = MutableLiveData<Result<String>>()
    val isLoading = MutableLiveData<Boolean>()

    fun loadComments(page: Int) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val result = commentRepository.getAllComments(page)
                comments.value = Result.success(result.getOrThrow())
            } catch (e: Exception) {
                comments.value = Result.failure(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadFixDeskRequests() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val result = fixDeskRequestRepository.getAllFixDeskRequests()
                fixDeskRequests.value = Result.success(result.getOrThrow())
            } catch (e: Exception) {
                fixDeskRequests.value = Result.failure(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun confirmAndDeclineFixDeskRequest(id: String, status: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val update = FixDeskRequestUpdate(id, status)
                val result = fixDeskRequestRepository.updateFixDeskRequest(update)
                result.fold(
                    onSuccess = {
                        // Aktualisieren Sie die Liste der FixDeskRequests mit dem neuen Status
                        val updatedList = fixDeskRequests.value?.getOrThrow()?.map {
                            if (it.id == id) it.copy(status = status) else it // Ändern Sie dies entsprechend Ihrer Datenstruktur
                        }
                        fixDeskRequests.value = Result.success(updatedList ?: listOf())

                        // Aktualisieren Sie die UI mit der Erfolgsmeldung
                        val message = if (status == "approved") {
                            "Anfrage erfolgreich bestätigt."
                        } else {
                            "Anfrage erfolgreich abgelehnt."
                        }
                        updateResult.value = Result.success(message)



                    },
                    onFailure = { e ->
                        updateResult.value = Result.failure(e)
                    }
                )
            } finally {
                isLoading.value = false
            }
        }
    }
}
