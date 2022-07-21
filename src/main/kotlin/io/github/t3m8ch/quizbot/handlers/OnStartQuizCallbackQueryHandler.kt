package io.github.t3m8ch.quizbot.handlers

import io.github.t3m8ch.quizbot.constants.START_QUIZ
import io.github.t3m8ch.quizbot.context.Context
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery

@Component
class OnStartQuizCallbackQueryHandler : Handler(
    { it.update.callbackQuery?.data?.startsWith(START_QUIZ) ?: false }
) {
    override fun handle(context: Context) {
        val quizId = context.update.callbackQuery?.data?.split(':')?.getOrElse(1) {
            // TODO: Change to custom exception
            throw RuntimeException("No QuizId in callback data")
        }

        // TODO: Create real implementation
        val answerMethod = AnswerCallbackQuery.builder()
            .callbackQueryId(context.update.callbackQuery.id)
            .text(quizId)
            .build()

        context.sender.execute(answerMethod)
    }
}
