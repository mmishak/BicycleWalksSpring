package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.api.LoginData
import ru.mmishak.bicyclewalksspring.model.api.Organizer
import ru.mmishak.bicyclewalksspring.model.mappers.OrganizerMapper
import ru.mmishak.bicyclewalksspring.model.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository

@RestController
@RequestMapping("/api/organizers")
class OrganizersRestController(private val organizers: OrganizersRepository, walks: WalksRepository) {

    private val mapper = OrganizerMapper(organizers, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Organizer> = organizers.findAll().map(mapper::toApi)

    @PostMapping("/registration")
    fun create(@RequestBody organizer: Organizer): Any = when {
        organizers.findAll().any { it.login == organizer.login || it.email == organizer.email } -> ElementAlreadyExists()
        else -> mapper.toApi(organizers.save(mapper.toModel(organizer)))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginData): Any {
        val (login, password) = loginData
        return organizers.findAll()
            .find { it.login == login && it.password == password }
            ?.let { mapper.toApi(it) }
            ?: ElementNotFound()

    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        mapper.toApi(organizers.findById(id).get())
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        organizers.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }
}