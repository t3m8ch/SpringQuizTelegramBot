package io.github.t3m8ch.quizbot.handlers.quizzesPaginator

import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGE_INDEX
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGINATOR_MESSAGE_ID
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.services.QuizService
import io.github.t3m8ch.quizbot.utils.MessageRevoker
import io.github.t3m8ch.quizbot.utils.buildQuizzesPaginatorKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class OnQuizzesCommandHandler(private val quizService: QuizService) : Handler(
    { it.update.message?.text == "/quizzes" && it.update.message.isUserMessage }
) {
    override fun handle(context: Context) {
        val pageIndex = 0
        val payload = context.user.session.payload

        val paginatorMessageId = payload.getOrDefault(QUIZZES_PAGINATOR_MESSAGE_ID, null) as Int?
        MessageRevoker(context).revokeMessage(paginatorMessageId, QUIZZES_PAGINATOR_MESSAGE_ID)

        payload[QUIZZES_PAGE_INDEX] = pageIndex

        val quizzesPage = quizService.getPage(pageIndex, pageSize = 6)
        val sendMessage = SendMessage.builder()
            .text("Выберите квиз, который вы хотите пройти:")
            .replyMarkup(buildQuizzesPaginatorKeyboard(quizzesPage, pageIndex))
            .chatId(context.update.message.chatId)
            .build()

        val message = context.sender.execute(sendMessage)
        payload[QUIZZES_PAGINATOR_MESSAGE_ID] = message.messageId
    }
}
