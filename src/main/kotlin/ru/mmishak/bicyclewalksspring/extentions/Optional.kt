package ru.mmishak.bicyclewalksspring.extentions

import java.util.*

fun <T> Optional<T>.getOrNull(): T? = try {
    get()
} catch (e: Exception) {
    null
}