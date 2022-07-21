package io.github.t3m8ch.quizbot.handlers

import io.github.t3m8ch.quizbot.context.Context

abstract class Handler(val filter: (Context) -> Boolean) {
    abstract fun handle(context: Context)
}
