package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.mappers.WalkMapper
import ru.mmishak.bicyclewalksspring.model.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.model.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.model.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.model.api.BicycleWalk as ApiWalk

@RestController
@RequestMapping("/api/walks")
class WalksRestController(
    private val walks: WalksRepository,
    organizers: OrganizersRepository,
    cyclists: CyclistsRepository,
    leaders: LeadersRepository
) {
    private val mapper = WalkMapper(organizers, cyclists, leaders)

    @GetMapping("/all")
    fun getAll(): Iterable<ApiWalk> = walks.findAll().map(mapper::toApi)

    @PostMapping("/create")
    fun create(@RequestBody walk: ApiWalk): Any = when {
        walks.existsById(walk.id) -> ElementAlreadyExists()
        else -> mapper.toApi(walks.save(mapper.toModel(walk)))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        mapper.toApi(walks.findById(id).get())
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        walks.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }

    @PostMapping("/change")
    fun update(@RequestBody walk: ApiWalk): Any = when {
        walks.existsById(walk.id) -> mapper.toApi(walks.save(mapper.toModel(walk)))
        else -> ElementNotFound()
    }
}