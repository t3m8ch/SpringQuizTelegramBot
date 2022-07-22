package io.github.t3m8ch.quizbot.handlers.quizzing

import io.github.t3m8ch.quizbot.constants.CURRENT_QUESTION_INDEX
import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ
import io.github.t3m8ch.quizbot.constants.QUIZZES_PAGINATOR_MESSAGE_ID
import io.github.t3m8ch.quizbot.constants.START_QUIZ
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.services.QuizService
import io.github.t3m8ch.quizbot.utils.MessageRevoker
import io.github.t3m8ch.quizbot.utils.QuestionReceiver
import io.github.t3m8ch.quizbot.utils.QuestionSender
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery

@Component
class OnStartQuizCallbackQueryHandler(private val quizService: QuizService) : Handler(
    { it.update.callbackQuery?.data?.startsWith(START_QUIZ) ?: false }
) {
    override fun handle(context: Context) {
        val quizId = context.update.callbackQuery?.data?.split(':')?.getOrElse(1) {
            // TODO: Change to custom exception
            throw RuntimeException("No QuizId in callback data")
        }

        val quiz = quizService.getById(ObjectId(quizId))
        val payload = context.user.session.payload

        val paginatorMessageId = payload.getOrDefault(QUIZZES_PAGINATOR_MESSAGE_ID, null) as Int?
        MessageRevoker(context).revokeMessage(paginatorMessageId, QUIZZES_PAGINATOR_MESSAGE_ID)

        payload[CURRENT_QUIZ] = quiz
        val currentQuestionIndex = 0
        payload[CURRENT_QUESTION_INDEX] = currentQuestionIndex

        val currentQuestion = QuestionReceiver(context, quiz).receiveOrEndQuiz(currentQuestionIndex) ?: return
        QuestionSender(context).send(currentQuestion)

        context.sender.execute(AnswerCallbackQuery.builder().callbackQueryId(context.update.callbackQuery.id).build())
    }
}
