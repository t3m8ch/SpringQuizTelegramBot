package io.github.t3m8ch.quizbot.repositories

import io.github.t3m8ch.quizbot.models.UserModel
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserModel, ObjectId> {
    fun findOrNullByTelegramId(telegramId: Long): UserModel?
}
