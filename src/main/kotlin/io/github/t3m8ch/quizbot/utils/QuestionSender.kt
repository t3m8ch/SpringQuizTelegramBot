package io.github.t3m8ch.quizbot.utils

import io.github.t3m8ch.quizbot.context.Context
import io.github.t3m8ch.quizbot.models.Question
import org.telegram.abilitybots.api.util.AbilityUtils
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll

class QuestionSender(private val context: Context) {
    fun send(question: Question) {
        val sendPoll = SendPoll.builder()
            .chatId(AbilityUtils.getChatId(context.update))
            .question(question.text)
            .options(question.possibleAnswers)
            .type("quiz")
            .allowMultipleAnswers(false)
            .isAnonymous(false)
            .correctOptionId(question.correctAnswerIndex)
            .build()

        context.sender.execute(sendPoll)
    }
}
