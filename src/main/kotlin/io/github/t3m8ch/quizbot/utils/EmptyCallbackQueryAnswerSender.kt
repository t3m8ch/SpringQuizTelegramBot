package io.github.t3m8ch.quizbot.utils

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

class EmptyCallbackQueryAnswerSender(private val sender: AbsSender) {
    fun send(callbackQueryId: String) {
        val answer = AnswerCallbackQuery.builder()
            .callbackQueryId(callbackQueryId)
            .build()
        sender.execute(answer)
    }
}
