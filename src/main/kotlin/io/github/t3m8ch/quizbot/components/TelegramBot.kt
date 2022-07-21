package io.github.t3m8ch.quizbot.components

import io.github.t3m8ch.quizbot.config.TelegramBotProps
import io.github.t3m8ch.quizbot.handlers.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramBot(
    private val botProps: TelegramBotProps,
    private val handlers: List<Handler>
) : TelegramLongPollingBot() {
    override fun getBotToken() = botProps.botToken
    override fun getBotUsername() = botProps.botUsername

    override fun onUpdateReceived(update: Update) {
        val context = Context(update, this)
        val handler = handlers.firstOrNull { it.filter(context) } ?: return
        handler.handle(context)
    }
}
