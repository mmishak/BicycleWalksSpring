package ru.mmishak.bicyclewalksspring.index

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import ru.mmishak.bicyclewalksspring.visit.Visit
import ru.mmishak.bicyclewalksspring.visit.VisitRepository
import java.time.LocalDateTime

@Controller
class IndexController(var visitRepository: VisitRepository) {

    @GetMapping("/index")
    fun index(model: Model): String {

        val visit = Visit(0, description = "Visited at ${LocalDateTime.now()}")
        visitRepository.save(visit)

        model.addAttribute("name", "World")

        return "index"
    }
}