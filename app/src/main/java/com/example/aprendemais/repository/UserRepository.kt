package com.example.aprendemais.repository

import com.example.aprendemais.database.User
import com.example.aprendemais.database.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun inserirUsuario(user: User): Long {
        return userDao.inserirUsuario(user)
    }

    suspend fun obterUsuarioPorEmail(email: String): User? {
        return userDao.obterUsuarioPorEmail(email)
    }

    suspend fun obterTodosUsuarios(): List<User> {
        return userDao.obterTodosUsuarios()
    }

    suspend fun atualizarUsuario(user: User) {
        userDao.atualizarUsuario(user)
    }
}