package io.github.t3m8ch.quizbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class TelegramBotProps(
    @Value("\${telegram.bot-token}") val botToken: String,
    @Value("\${telegram.bot-username}") val botUsername: String,
)
