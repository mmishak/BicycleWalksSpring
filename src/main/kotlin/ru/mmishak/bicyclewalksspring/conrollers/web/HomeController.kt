package ru.mmishak.bicyclewalksspring.conrollers.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @RequestMapping("/home")
    fun home() = "index"
}