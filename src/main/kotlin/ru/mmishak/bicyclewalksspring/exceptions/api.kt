@file:Suppress("FunctionName")

package ru.mmishak.bicyclewalksspring.exceptions

data class Error(val error: String)

fun ElementAlreadyExists() = Error("Element already exists")
fun ElementNotFound() = Error("Element not found")