package ru.mmishak.bicyclewalksspring.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.api.entity.Cyclist
import ru.mmishak.bicyclewalksspring.api.mappers.CyclistMapper
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository

@RestController
@RequestMapping("/api/cyclists")
class CyclistsRestController(private val repository: CyclistsRepository, walks: WalksRepository) {

    private val mapper = CyclistMapper(repository, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Cyclist> = repository.findAll().map(mapper::transform)

    @PostMapping("/registration")
    fun create(@RequestBody organizer: Cyclist): Any = when {
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
    fun update(@RequestBody organizer: Cyclist): Any = when {
        repository.existsById(organizer.id) -> mapper.transform(repository.save(mapper.transform(organizer)))
        else -> ElementNotFound()
    }
}