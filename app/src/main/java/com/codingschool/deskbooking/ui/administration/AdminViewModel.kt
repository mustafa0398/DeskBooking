package com.codingschool.deskbooking.ui.administration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRequestUpdate
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.repository.AdminCommentRepository
import com.codingschool.deskbooking.data.repository.FixDeskRequestRepository
import kotlinx.coroutines.launch

class AdminViewModel(
    private val commentRepository: AdminCommentRepository,
    private val fixDeskRequestRepository: FixDeskRequestRepository
) : ViewModel() {
    val comments = MutableLiveData<Result<List<CommentResponse>>>()
    val fixDeskRequests = MutableLiveData<Result<List<FixDeskResponse>>>()
    val updateResult = MutableLiveData<Result<String>>()
    private val isLoading = MutableLiveData<Boolean>()


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

                        val updatedList = fixDeskRequests.value?.getOrThrow()?.map {
                            if (it.id == id) it.copy(status = status) else it
                        }
                        fixDeskRequests.value = Result.success(updatedList ?: listOf())

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
