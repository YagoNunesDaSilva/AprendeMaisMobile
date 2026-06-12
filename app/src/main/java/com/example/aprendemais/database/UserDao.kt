package com.example.aprendemais.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun inserirUsuario(user: User): Long

    @Update
    suspend fun atualizarUsuario(user: User)

    @Delete
    suspend fun deletarUsuario(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun obterUsuarioPorId(userId: Int): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun obterUsuarioPorEmail(email: String): User?

    @Query("SELECT * FROM users")
    suspend fun obterTodosUsuarios(): List<User>

    @Query("DELETE FROM users")
    suspend fun limparTodosUsuarios()
}