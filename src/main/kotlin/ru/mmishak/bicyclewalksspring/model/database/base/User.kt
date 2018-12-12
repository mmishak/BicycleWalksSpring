package ru.mmishak.bicyclewalksspring.model.database.base

interface User {
    val login: String
    val password: String
    val email: String
    val name: String
}