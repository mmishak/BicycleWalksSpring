package ru.mmishak.bicyclewalksspring.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mmishak.bicyclewalksspring.model.database.BicycleWalk

@Repository
interface WalksRepository : CrudRepository<BicycleWalk, Long>