package ru.mmishak.bicyclewalksspring.index

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import ru.mmishak.bicyclewalksspring.visit.Visit
import ru.mmishak.bicyclewalksspring.visit.VisitRepository
import java.time.LocalDateTime

@Controller
class IndexController(var visitRepository: VisitRepository) {

    @GetMapping("/")
    fun index(model: Model): String {

        val visit = Visit(0, description = "Visited at ${LocalDateTime.now()}")
        visitRepository.save(visit)

        if (!model.containsAttribute("name")) {
            model["name"] = "World"
        }

        return "index"
    }
}