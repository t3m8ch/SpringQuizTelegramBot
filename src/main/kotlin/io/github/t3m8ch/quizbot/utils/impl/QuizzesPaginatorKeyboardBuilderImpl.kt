package io.github.t3m8ch.quizbot.utils.impl

import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.NEXT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.PREVIOUS_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.START_QUIZ
import io.github.t3m8ch.quizbot.services.QuizService
import io.github.t3m8ch.quizbot.utils.QuizzesPaginatorKeyboardBuilder
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Component
class QuizzesPaginatorKeyboardBuilderImpl(private val quizService: QuizService) : QuizzesPaginatorKeyboardBuilder {
    override fun build(pageIndex: Int, pageSize: Int): InlineKeyboardMarkup {
        val quizzes = quizService.getAll(pageIndex, pageSize)
        val keyboard = quizzes.mapIndexed { idx, quiz ->
            listOf(
                InlineKeyboardButton.builder()
                    .text("${idx + 1}. ${quiz.name}")
                    .callbackData("$START_QUIZ:${quiz.id}")
                    .build()
            )
        } + listOf(
            listOf(
                InlineKeyboardButton.builder()
                    .text("<<")
                    .callbackData(PREVIOUS_QUIZ_PAGE)
                    .build(),
                InlineKeyboardButton.builder()
                    .text("${pageIndex + 1}/${quizzes.totalPages}")
                    .callbackData(CURRENT_QUIZ_PAGE)
                    .build(),
                InlineKeyboardButton.builder()
                    .text(">>")
                    .callbackData(NEXT_QUIZ_PAGE)
                    .build(),
            )
        )

        return InlineKeyboardMarkup(keyboard)
    }
}
