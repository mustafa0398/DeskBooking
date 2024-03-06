package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.offices.Offices
import com.codingschool.deskbooking.service.api.RetrofitClient

class DesksRepository {
    suspend fun getDesksById(): List<Desk> {
        val response = RetrofitClient.authenticationService.getDesksById()
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            emptyList()
        }
    }

    suspend fun getOfficesById(id : String): Offices {
        val response = RetrofitClient.authenticationService.getOfficeById(id)
        return response.body()!!
    }
}
