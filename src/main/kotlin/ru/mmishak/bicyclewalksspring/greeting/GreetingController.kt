package ru.mmishak.bicyclewalksspring.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @GetMapping("/greeting")
    fun index(@RequestParam(value = "name", defaultValue = "World") name: String) = Greeting("Hello, $name!")
}