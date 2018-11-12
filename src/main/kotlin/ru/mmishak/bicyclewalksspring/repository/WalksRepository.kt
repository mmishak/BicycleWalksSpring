package ru.mmishak.bicyclewalksspring.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mmishak.bicyclewalksspring.entity.BicycleWalk

@Repository
interface WalksRepository : CrudRepository<BicycleWalk, Long>