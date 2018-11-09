package ru.mmishak.bicyclewalksspring.visit

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VisitRepository : CrudRepository<Visit, Long>