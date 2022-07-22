package io.github.t3m8ch.quizbot.utils

import io.github.t3m8ch.quizbot.context.Context
import org.slf4j.LoggerFactory
import org.telegram.abilitybots.api.util.AbilityUtils.getChatId
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class MessageRevoker(private val context: Context) {
    fun revokeMessage(messageId: Int?, payloadKey: String) {
        if (messageId == null) return

        val payload = context.user.session.payload

        val deleteMessage = DeleteMessage.builder()
            .chatId(getChatId(context.update))
            .messageId(messageId)
            .build()

        try {
            context.sender.execute(deleteMessage)
        } catch (e: TelegramApiException) {
            logger.warn("Failed to delete message with ID: $messageId", e)
        }

        payload[payloadKey] = null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MessageRevoker::class.java)
    }
}
