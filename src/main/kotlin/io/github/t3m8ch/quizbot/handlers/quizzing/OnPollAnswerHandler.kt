package io.github.t3m8ch.quizbot.handlers.quizzing

import io.github.t3m8ch.quizbot.constants.CURRENT_QUESTION_INDEX
import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ
import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.handlers.Handler
import io.github.t3m8ch.quizbot.models.QuizModel
import io.github.t3m8ch.quizbot.utils.QuestionReceiver
import io.github.t3m8ch.quizbot.utils.QuestionSender
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.util.AbilityUtils.getChatId
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class OnPollAnswerHandler : Handler({ it.update.hasPollAnswer() }) {
    override fun handle(context: Context) {
        val payload = context.user.session.payload

        val currentQuiz = payload.getOrElse(CURRENT_QUIZ) { ifFieldIsNull(CURRENT_QUIZ, context) } as QuizModel
        var currentQuestionIndex = payload.getOrElse(CURRENT_QUESTION_INDEX) {
            ifFieldIsNull(CURRENT_QUESTION_INDEX, context)
        } as Int

        payload[CURRENT_QUESTION_INDEX] = ++currentQuestionIndex

        val currentQuestion = QuestionReceiver(context, currentQuiz).receiveOrEndQuiz(currentQuestionIndex) ?: return
        QuestionSender(context).send(currentQuestion)
    }

    private fun ifFieldIsNull(fieldName: String, context: Context) {
        context.sender.execute(
            SendMessage.builder().text("Произошла ошибка не сервере").chatId(getChatId(context.update)).build()
        )
        logger.error("When processing PollAnswer it turned out that $fieldName is null", context)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(OnPollAnswerHandler::class.java)
    }
}
