package io.github.t3m8ch.quizbot.handlers.quizzesPaginator

import io.github.t3m8ch.quizbot.constants.NEXT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGE_INDEX
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGINATOR_MESSAGE_ID
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.services.QuizService
import io.github.t3m8ch.quizbot.utils.EmptyCallbackQueryAnswerSender
import io.github.t3m8ch.quizbot.utils.buildQuizzesPaginatorKeyboard
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.util.AbilityUtils.getChatId
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup

@Component
class OnNextQuizPageCallbackQueryHandler(private val quizService: QuizService) : Handler(
    { it.update.callbackQuery?.data == NEXT_QUIZ_PAGE }
) {
    override fun handle(context: Context) {
        val payload = context.user.session.payload
        var pageIndex = payload[QUIZZES_PAGE_INDEX] as Int
        val quizzesPage = quizService.getPage(pageIndex + 1, pageSize = 6)

        if (quizzesPage.isEmpty) {
            EmptyCallbackQueryAnswerSender(context.sender).send(context.update.callbackQuery.id)
            return
        }

        payload[QUIZZES_PAGE_INDEX] = ++pageIndex

        val messageId = payload[QUIZZES_PAGINATOR_MESSAGE_ID] as Int

        val editMessage = EditMessageReplyMarkup.builder()
            .replyMarkup(buildQuizzesPaginatorKeyboard(quizzesPage, pageIndex))
            .chatId(getChatId(context.update))
            .messageId(messageId)
            .build()

        context.sender.execute(editMessage)
    }
}
