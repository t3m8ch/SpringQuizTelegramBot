package io.github.t3m8ch.quizbot.context

import io.github.t3m8ch.quizbot.models.UserModel
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

data class Context(
    val update: Update,
    val sender: AbsSender,
    val user: UserModel,
)
