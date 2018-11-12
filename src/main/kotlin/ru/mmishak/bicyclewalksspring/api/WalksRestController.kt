package ru.mmishak.bicyclewalksspring.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.api.mappers.WalkMapper
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.api.entity.BicycleWalk as ApiWalk

@RestController
@RequestMapping("/api/walks")
class WalksRestController(
    private val repository: WalksRepository,
    organizers: OrganizersRepository,
    cyclists: CyclistsRepository,
    leaders: LeadersRepository
) {
    private val mapper = WalkMapper(organizers, cyclists, leaders)

    @GetMapping("/all")
    fun getAll(): Iterable<ApiWalk> = repository.findAll().map(mapper::transform)

    @PostMapping("/create")
    fun create(@RequestBody walk: ApiWalk): Any = when {
        repository.existsById(walk.id) -> ElementAlreadyExists()
        else -> mapper.transform(repository.save(mapper.transform(walk)))
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
    fun update(@RequestBody walk: ApiWalk): Any = when {
        repository.existsById(walk.id) -> mapper.transform(repository.save(mapper.transform(walk)))
        else -> ElementNotFound()
    }
}