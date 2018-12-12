package ru.mmishak.bicyclewalksspring.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mmishak.bicyclewalksspring.model.database.Cyclist

@Repository
interface CyclistsRepository : CrudRepository<Cyclist, Long>