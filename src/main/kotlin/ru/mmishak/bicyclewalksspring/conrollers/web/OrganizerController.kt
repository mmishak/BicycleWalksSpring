package ru.mmishak.bicyclewalksspring.conrollers.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.mmishak.bicyclewalksspring.model.database.Organizer
import ru.mmishak.bicyclewalksspring.repository.OrganizersRepository

@Controller
@RequestMapping("/organizers")
class OrganizerController(private val repository: OrganizersRepository) {

    @GetMapping
    fun all(model: Model): String {
        model["organizers"] = repository.findAll().toList()
        return "organizers_list"
    }

    @GetMapping("/sign_up")
    fun singUpForm(model: Model): String {
        model["action"] = "sign_up"
        model["title"] = "Organizer registration"
        model["login"] = Organizer::login.name
        model["password"] = Organizer::password.name
        model["email"] = Organizer::email.name
        model["name"] = Organizer::name.name
        return "sign_up"
    }

    @PostMapping("/sign_up")
    fun addOrganizer(@ModelAttribute organizer: Organizer): String {
        repository.save(organizer)
        return "redirect:"
    }
}