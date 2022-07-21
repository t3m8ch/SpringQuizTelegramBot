package io.github.t3m8ch.quizbot.utils

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup

interface QuizzesPaginatorKeyboardBuilder {
    fun build(pageIndex: Int = 0, pageSize: Int = 6): InlineKeyboardMarkup
}
