package io.github.t3m8ch.quizbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizBotApplication

fun main(args: Array<String>) {
    runApplication<QuizBotApplication>(*args)
}
