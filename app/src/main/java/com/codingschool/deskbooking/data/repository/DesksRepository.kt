package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.local.dao.DeskDao
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.desks.toDesk
import com.codingschool.deskbooking.data.model.authentication.desks.toDeskRoom
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DesksRepository{
    suspend fun loadAllDesks()
    suspend fun saveDesks(desksList: List<Desk>)
    suspend fun getSavedDesks() : Flow<List<Desk>>
}

class DesksRepositoryImpl(val deskDao: DeskDao) : DesksRepository {
    override suspend fun loadAllDesks() {
        RetrofitClient.authenticationService.getAllDesks().let {response ->
             if (response.isSuccessful) {
                val deskList = response.body()?: emptyList()
                saveDesks(deskList)
            }
        }
    }

    override suspend fun saveDesks(desksList: List<Desk>) {
        deskDao.insertDesk(desksList.map { it.toDeskRoom() })
    }

    override suspend fun getSavedDesks(): Flow<List<Desk>> {
        return deskDao.getAllDesks().map { it.map { deskRoom -> deskRoom.toDesk() } }
    }
}
