package io.github.t3m8ch.quizbot.utils

import io.github.t3m8ch.quizbot.constants.CURRENT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.NEXT_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.PREVIOUS_QUIZ_PAGE
import io.github.t3m8ch.quizbot.constants.START_QUIZ
import io.github.t3m8ch.quizbot.models.QuizModel
import org.springframework.data.domain.Page
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun buildQuizzesPaginatorKeyboard(quizzesPage: Page<QuizModel>, pageIndex: Int): InlineKeyboardMarkup {
    val keyboard = quizzesPage.mapIndexed { idx, quiz ->
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
                .text("${pageIndex + 1}/${quizzesPage.totalPages}")
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
