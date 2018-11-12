package ru.mmishak.bicyclewalksspring.entity

import ru.mmishak.bicyclewalksspring.entity.base.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import ru.mmishak.bicyclewalksspring.entity.base.Entity as DbEntity

@Entity
data class Leader(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,
    override val login: String,
    override val email: String,
    override val password: String,
    override val name: String
) : DbEntity, User