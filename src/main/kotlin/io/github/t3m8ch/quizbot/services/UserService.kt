package io.github.t3m8ch.quizbot.services

import io.github.t3m8ch.quizbot.models.UserModel

interface UserService {
    fun createOrGetUser(telegramId: Long): UserModel
}
