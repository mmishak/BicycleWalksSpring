package ru.mmishak.bicyclewalksspring.entity.base

interface User {
    val login: String
    val password: String
    val email: String
    val name: String
}