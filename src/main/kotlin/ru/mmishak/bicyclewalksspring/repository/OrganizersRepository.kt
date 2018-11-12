package ru.mmishak.bicyclewalksspring.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mmishak.bicyclewalksspring.entity.Organizer

@Repository
interface OrganizersRepository : CrudRepository<Organizer, Long>