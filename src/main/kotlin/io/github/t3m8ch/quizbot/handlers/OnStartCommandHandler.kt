package io.github.t3m8ch.quizbot.handlers

import io.github.t3m8ch.quizbot.context.Context
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class OnStartCommandHandler : Handler({ it.update.message?.text == "/start" }) {
    override fun handle(context: Context) {
        val sendMessage = SendMessage.builder().text("Hello, world!").chatId(context.update.message.from.id).build()
        context.sender.execute(sendMessage)
    }
}
