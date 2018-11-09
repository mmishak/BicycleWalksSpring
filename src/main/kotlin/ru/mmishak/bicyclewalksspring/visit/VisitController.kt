package ru.mmishak.bicyclewalksspring.visit

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitController(val repository: VisitRepository) {

    @GetMapping("/visits")
    fun visits() = repository.findAll().toList()
}