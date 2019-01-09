package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.api.Leader
import ru.mmishak.bicyclewalksspring.model.api.LoginData
import ru.mmishak.bicyclewalksspring.model.mappers.LeaderMapper
import ru.mmishak.bicyclewalksspring.model.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository

@RestController
@RequestMapping("/api/leaders")
class LeadersRestController(private val leaders: LeadersRepository, walks: WalksRepository) {

    private val mapper = LeaderMapper(leaders, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Leader> = leaders.findAll().map(mapper::toApi)

    @PostMapping("/registration")
    fun create(@RequestBody leader: Leader): Any = when {
        leaders.findAll().any { it.login == leader.login || it.email == leader.email } -> ElementAlreadyExists()
        else -> mapper.toApi(leaders.save(mapper.toModel(leader)))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginData): Any {
        val (login, password) = loginData
        return leaders.findAll()
            .find { it.login == login && it.password == password }
            ?.let { mapper.toApi(it) }
            ?: ElementNotFound()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        mapper.toApi(leaders.findById(id).get())
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        leaders.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }
}