package io.github.t3m8ch.quizbot.repositories

import io.github.t3m8ch.quizbot.models.QuizModel
import org.bson.types.ObjectId
import org.springframework.data.repository.PagingAndSortingRepository

interface QuizRepository : PagingAndSortingRepository<QuizModel, ObjectId>
