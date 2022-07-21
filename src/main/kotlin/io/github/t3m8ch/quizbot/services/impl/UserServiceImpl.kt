package io.github.t3m8ch.quizbot.services.impl

import io.github.t3m8ch.quizbot.models.UserModel
import io.github.t3m8ch.quizbot.repositories.UserRepository
import io.github.t3m8ch.quizbot.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun createOrGetUser(telegramId: Long): UserModel {
        return userRepository.findOrNullByTelegramId(telegramId)
            ?: return userRepository.save(UserModel(telegramId = telegramId))
    }
}
