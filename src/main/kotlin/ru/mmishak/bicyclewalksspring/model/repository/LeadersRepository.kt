package ru.mmishak.bicyclewalksspring.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mmishak.bicyclewalksspring.model.database.Leader

@Repository
interface LeadersRepository : CrudRepository<Leader, Long>