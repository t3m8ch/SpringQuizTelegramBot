package io.github.t3m8ch.quizbot.handlers.quizzesCommandHandlers

import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery

@Component
class OnCurrentQuizPageCallbackQueryHandler : Handler(
    { it.update.callbackQuery?.data == CURRENT_QUIZ_PAGE }
) {
    override fun handle(context: Context) {
        val answer = AnswerCallbackQuery.builder()
            .callbackQueryId(context.update.callbackQuery.id)
            .build()
        context.sender.execute(answer)
    }
}
