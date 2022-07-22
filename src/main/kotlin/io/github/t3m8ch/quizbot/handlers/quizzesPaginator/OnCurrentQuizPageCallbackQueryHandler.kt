package io.github.t3m8ch.quizbot.handlers.quizzesPaginator

import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.utils.EmptyCallbackQueryAnswerSender
import org.springframework.stereotype.Component

@Component
class OnCurrentQuizPageCallbackQueryHandler : Handler({ it.update.callbackQuery?.data == CURRENT_QUIZ_PAGE }) {
    override fun handle(context: Context) {
        EmptyCallbackQueryAnswerSender(context.sender).send(context.update.callbackQuery.id)
    }
}
