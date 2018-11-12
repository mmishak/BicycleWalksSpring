package ru.mmishak.bicyclewalksspring.api.entity

data class Leader(
    val id: Long = 0,
    val login: String,
    val email: String,
    val password: String? = null,
    val name: String,
    val bicycleWalksIds: List<Long> = listOf()
)