package ru.mmishak.bicyclewalksspring.index

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IndexController {

    @GetMapping("/")
    fun index(@RequestParam(value = "name", defaultValue = "World") name: String, model: Model): String {
        model["name"] = name
        return "index"
    }
}