package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.api.Leader
import ru.mmishak.bicyclewalksspring.model.mappers.LeaderMapper
import ru.mmishak.bicyclewalksspring.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository

@RestController
@RequestMapping("/api/leaders")
class LeadersRestController(private val repository: LeadersRepository, walks: WalksRepository) {

    private val mapper = LeaderMapper(repository, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Leader> = repository.findAll().map(mapper::transform)

    @PostMapping("/registration")
    fun create(@RequestBody organizer: Leader): Any = when {
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
    fun update(@RequestBody organizer: Leader): Any = when {
        repository.existsById(organizer.id) -> mapper.transform(repository.save(mapper.transform(organizer)))
        else -> ElementNotFound()
    }
}