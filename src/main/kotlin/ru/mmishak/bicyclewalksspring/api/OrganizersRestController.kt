package ru.mmishak.bicyclewalksspring.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.api.entity.Organizer
import ru.mmishak.bicyclewalksspring.api.mappers.OrganizerMapper
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository

@RestController
@RequestMapping("/api/organizers")
class OrganizersRestController(private val repository: OrganizersRepository, walks: WalksRepository) {

    private val mapper = OrganizerMapper(repository, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Organizer> = repository.findAll().map(mapper::transform)

    @PostMapping("/registration")
    fun create(@RequestBody organizer: Organizer): Any = when {
        repository.existsById(organizer.id) -> ElementAlreadyExists()
        else -> mapper.transform(repository.save(mapper.transform(organizer)))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        mapper.transform(repository.findById(id).get())
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        repository.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }

    @PostMapping("/change")
    fun update(@RequestBody organizer: Organizer): Any = when {
        repository.existsById(organizer.id) -> mapper.transform(repository.save(mapper.transform(organizer)))
        else -> ElementNotFound()
    }
}