package com.codingschool.deskbooking.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.codingschool.deskbooking.data.model.authentication.offices.Office
import com.codingschool.deskbooking.data.model.dto.desks.FixDesk
import com.codingschool.deskbooking.data.model.dto.user.User
import java.lang.reflect.Type

class ObjectConverter {
    private val gson = Gson()

   @TypeConverter
    fun fromFixDesk(fixDesk: FixDesk): String {
        return gson.toJson(fixDesk)
    }

    @TypeConverter
    fun toFixDesk(json: String): FixDesk {
        val type: Type = object : TypeToken<FixDesk>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromOffices(office: Office): String {
        return gson.toJson(office)
    }

    @TypeConverter
    fun toOffices(json: String): Office {
        val type: Type = object : TypeToken<Office>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromUser(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun toUser(json: String): User {
        val type: Type = object : TypeToken<User>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromStringList(equipment: List<String>): String {
        return gson.toJson(equipment)
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

}