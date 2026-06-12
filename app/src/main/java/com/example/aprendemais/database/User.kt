package com.example.aprendemais.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val cpf: String? = null,
    val birthDate: String? = null,
    val role: String = "student",
    val institutionId: String? = null,
    val classId: String? = null,
    val mustChangePassword: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)