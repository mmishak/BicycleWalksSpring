package ru.mmishak.bicyclewalksspring.status

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StatusController {

    @GetMapping("/status")
    fun status() = "status"
}