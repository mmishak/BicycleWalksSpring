package ru.mmishak.bicyclewalksspring.visit

import javax.persistence.*

@Entity
data class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var description: String
)