package com.example.vshcheglov.simpleweather.domain.commands

interface Command<out T> {
    fun execute(): T
}