package io.github.t3m8ch.quizbot.handlers

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

data class Context(
    val update: Update,
    val sender: AbsSender,
)

abstract class Handler(val filter: (Context) -> Boolean) {
    abstract fun handle(context: Context)
}
