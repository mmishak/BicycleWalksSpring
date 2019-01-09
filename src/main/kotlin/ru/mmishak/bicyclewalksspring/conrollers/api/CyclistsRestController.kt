package ru.mmishak.bicyclewalksspring.conrollers.api

import org.springframework.web.bind.annotation.*
import ru.mmishak.bicyclewalksspring.exceptions.ElementAlreadyExists
import ru.mmishak.bicyclewalksspring.exceptions.ElementNotFound
import ru.mmishak.bicyclewalksspring.model.api.Cyclist
import ru.mmishak.bicyclewalksspring.model.api.LoginData
import ru.mmishak.bicyclewalksspring.model.mappers.CyclistMapper
import ru.mmishak.bicyclewalksspring.model.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository

@RestController
@RequestMapping("/api/cyclists")
class CyclistsRestController(private val cyclists: CyclistsRepository, walks: WalksRepository) {

    private val mapper = CyclistMapper(cyclists, walks)

    @GetMapping("/all")
    fun getAll(): Iterable<Cyclist> = cyclists.findAll().map(mapper::toApi)

    @PostMapping("/registration")
    fun create(@RequestBody cyclist: Cyclist): Any = when {
        cyclists.findAll().any { it.login == cyclist.login || it.email == cyclist.email } -> ElementAlreadyExists()
        else -> mapper.toApi(cyclists.save(mapper.toModel(cyclist)))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginData): Any {
        val (login, password) = loginData
        return cyclists.findAll().find { it.login == login && it.password == password } ?: ElementNotFound()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: Long): Any = try {
        mapper.toApi(cyclists.findById(id).get())
    } catch (e: Exception) {
        ElementNotFound()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long) = try {
        cyclists.deleteById(id)
    } catch (e: Exception) {
        ElementNotFound()
    }

    @PostMapping("/change")
    fun update(@RequestBody cyclist: Cyclist): Any = when {
        cyclists.existsById(cyclist.id) -> mapper.toApi(cyclists.save(mapper.toModel(cyclist)))
        else -> ElementNotFound()
    }
}