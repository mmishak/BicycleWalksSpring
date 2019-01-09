package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.mappers.CyclistMapper
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
    private val cyclists: CyclistsRepository,
    leaders: LeadersRepository
) {
    private val mapper = WalkMapper(organizers, cyclists, leaders)
    private val cyclistMapper = CyclistMapper(cyclists, walks)

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

    @GetMapping("/{id}/cyclists")
    fun getCyclists(@PathVariable(value = "id") id: Long): Any = try {
        walks.findById(id).get().cyclists.map { cyclistMapper.toApi(it) }
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
        walks.existsById(walk.id) -> {
            val entity = walks.findById(walk.id).get()
            entity.title = walk.title
            entity.description = walk.description
            entity.distance = walk.distance
            entity.duration = walk.duration
            entity.date = walk.date
            entity.price = walk.price
            entity.leaderStatus = walk.leaderStatus
            entity.cyclists.clear()
            entity.cyclists.addAll(walk.cyclistsIds.map { cyclists.findById(it).get() })
            walks.save(entity)
            mapper.toApi(entity)
        }
        else -> ElementNotFound()
    }
}