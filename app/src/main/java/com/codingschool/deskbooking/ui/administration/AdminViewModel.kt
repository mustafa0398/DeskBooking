package com.codingschool.deskbooking.ui.administration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.CreatingDesk
import com.codingschool.deskbooking.data.model.dto.desks.CreatingDeskResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRequestUpdate
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.model.dto.offices.CreatingOffice
import com.codingschool.deskbooking.data.model.dto.offices.CreatingOfficeResponse
import com.codingschool.deskbooking.data.repository.AdminRepository
import com.codingschool.deskbooking.data.repository.FixDeskRequestRepository
import kotlinx.coroutines.launch



class AdminViewModel(private val adminRepository: AdminRepository, private val fixDeskRequestRepository: FixDeskRequestRepository) : ViewModel() {
    val comments = MutableLiveData<Result<List<CommentResponse>>>()
    val fixDeskRequests = MutableLiveData<Result<List<FixDeskResponse>>>()
    val updateResult = MutableLiveData<Result<String>>()
    private val isLoading = MutableLiveData<Boolean>()

    val officeCreationResult = MutableLiveData<Result<CreatingOfficeResponse>>()
    val deskCreationResult = MutableLiveData<Result<CreatingDeskResponse>>()

    fun loadComments(page: Int) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val result = adminRepository.getAllComments(page)
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
                            "Request successfully approved."
                        } else {
                            "Request successfully declined."
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

    fun createOffice(office: CreatingOffice) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = adminRepository.createOffice(office)
                response.fold(
                    onSuccess = { officeResponse ->
                        officeCreationResult.value = Result.success(officeResponse)
                        updateResult.value = Result.success("Room successfully created.")
                    },
                    onFailure = { error ->
                        officeCreationResult.value = Result.failure(error)
                        updateResult.value = Result.failure(Exception("Error creating room: ${error.message}"))
                    }
                )
            } catch (e: Exception) {
                officeCreationResult.value = Result.failure(e)
                updateResult.value = Result.failure(Exception("Error: ${e.localizedMessage}"))
            } finally {
                isLoading.value = false
            }
        }
    }


    fun createDesk(desk: CreatingDesk) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = adminRepository.createDesk(desk)
                response.fold(
                    onSuccess = { deskResponse ->
                        deskCreationResult.value = Result.success(deskResponse)
                        updateResult.value = Result.success("Desk successfully created.")
                    },
                    onFailure = { error ->
                        deskCreationResult.value = Result.failure(error)
                        updateResult.value = Result.failure(Exception("Error creating desk: ${error.message}"))
                    }
                )
            } catch (e: Exception) {
                deskCreationResult.value = Result.failure(e)
                updateResult.value = Result.failure(Exception("Error: ${e.localizedMessage}"))
            } finally {
                isLoading.value = false
            }
        }
    }

}
