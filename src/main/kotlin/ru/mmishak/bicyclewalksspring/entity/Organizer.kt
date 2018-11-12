package ru.mmishak.bicyclewalksspring.entity

import ru.mmishak.bicyclewalksspring.entity.base.User
import javax.persistence.*
import ru.mmishak.bicyclewalksspring.entity.base.Entity as DbEntity

@Entity
data class Organizer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,
    override val login: String,
    override val email: String,
    override val password: String,
    override val name: String,
    @OneToMany(mappedBy = "organizer")
    val bicycleWalks: MutableList<BicycleWalk> = mutableListOf()
) : DbEntity, User