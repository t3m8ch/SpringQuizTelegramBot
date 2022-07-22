package io.github.t3m8ch.quizbot.services

import io.github.t3m8ch.quizbot.models.QuizModel
import org.bson.types.ObjectId
import org.springframework.data.domain.Page

interface QuizService {
    fun getPage(pageIndex: Int = 0, pageSize: Int = 6): Page<QuizModel>
    fun getById(id: ObjectId): QuizModel
}
