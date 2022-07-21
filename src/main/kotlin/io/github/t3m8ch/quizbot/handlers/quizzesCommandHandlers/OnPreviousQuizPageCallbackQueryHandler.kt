package io.github.t3m8ch.quizbot.handlers.quizzesCommandHandlers

import io.github.t3m8ch.quizbot.constants.PREVIOUS_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGE_INDEX
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGINATOR_MESSAGE_ID
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.utils.QuizzesPaginatorKeyboardBuilder
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.util.AbilityUtils
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup

@Component
class OnPreviousQuizPageCallbackQueryHandler(private val keyboardBuilder: QuizzesPaginatorKeyboardBuilder) : Handler(
    { it.update.callbackQuery?.data == PREVIOUS_QUIZ_PAGE }
) {
    override fun handle(context: Context) {
        val payload = context.user.session.payload
        var pageIndex = payload[QUIZZES_PAGE_INDEX] as Int

        if (pageIndex <= 0) {
            val answer = AnswerCallbackQuery.builder()
                .callbackQueryId(context.update.callbackQuery.id)
                .build()
            context.sender.execute(answer)
            return
        }

        payload[QUIZZES_PAGE_INDEX] = --pageIndex

        val messageId = payload[QUIZZES_PAGINATOR_MESSAGE_ID] as Int

        val editMessage = EditMessageReplyMarkup.builder()
            .replyMarkup(keyboardBuilder.build(pageIndex, pageSize = 6))
            .chatId(AbilityUtils.getChatId(context.update))
            .messageId(messageId)
            .build()

        context.sender.execute(editMessage)
    }
}