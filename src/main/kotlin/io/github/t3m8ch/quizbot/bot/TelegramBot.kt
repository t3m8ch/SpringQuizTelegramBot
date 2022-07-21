package io.github.t3m8ch.quizbot.bot

import io.github.t3m8ch.quizbot.config.TelegramBotProps
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.services.UserService
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.util.AbilityUtils.getUser
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramBot(
    private val botProps: TelegramBotProps,
    private val userService: UserService,
    private val handlers: List<Handler>,
) : TelegramLongPollingBot() {
    override fun getBotToken() = botProps.botToken
    override fun getBotUsername() = botProps.botUsername

    override fun onUpdateReceived(update: Update) {
        val context = createContextFromUpdate(update)
        handle(context)
    }

    private fun handle(context: Context) {
        val handler = handlers.firstOrNull { it.filter(context) } ?: return
        handler.handle(context)
        userService.saveUser(context.user)
    }

    private fun createContextFromUpdate(update: Update): Context {
        val telegramUser = getUser(update)
        val userInDB = userService.createOrGetUser(telegramUser.id)
        return Context(update, this, userInDB)
    }
}
