package ru.mmishak.bicyclewalksspring.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.entity.Cyclist
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.repository.CyclistsRepository

@RestController
@RequestMapping("/api/cyclists")
class CyclistsRestController(val repository: CyclistsRepository) {

    @GetMapping("/all")
    fun getAll(): Iterable<Cyclist> = repository.findAll()

    @PostMapping("/registration")
    fun create(@RequestBody organizer: Cyclist): Any = when {
        repository.existsById(organizer.id) -> ElementAlreadyExists()
        else -> repository.save(organizer)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any {
        val organizer = repository.findById(id)
        return if (organizer.isPresent) organizer else ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        repository.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }

    @PostMapping("/change")
    fun update(@RequestBody organizer: Cyclist): Any = when {
        repository.existsById(organizer.id) -> repository.save(organizer)
        else -> ElementNotFound()
    }
}