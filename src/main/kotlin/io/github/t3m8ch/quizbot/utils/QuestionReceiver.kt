package io.github.t3m8ch.quizbot.utils

import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.models.Question
import io.github.t3m8ch.quizbot.models.QuizModel
import org.telegram.abilitybots.api.util.AbilityUtils
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class QuestionReceiver(private val context: Context, private val quiz: QuizModel) {
    fun receiveOrEndQuiz(questionIndex: Int): Question? {
        val question = quiz.questions.getOrNull(questionIndex)

        if (question == null) {
            val sendEndQuizMessage = SendMessage.builder()
                .text("Квиз окончен!")
                .chatId(AbilityUtils.getChatId(context.update))
                .build()
            context.sender.execute(sendEndQuizMessage)
            context.user.session.clean()
            return null
        }

        return question
    }
}
