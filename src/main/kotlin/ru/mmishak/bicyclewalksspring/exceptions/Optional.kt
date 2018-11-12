package ru.mmishak.bicyclewalksspring.exceptions

import java.util.*

fun <T> Optional<T>.getOrNull(): T? = try {
    get()
} catch (e: Exception) {
    null
}