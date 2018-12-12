package ru.mmishak.bicyclewalksspring.model.database

import ru.mmishak.bicyclewalksspring.model.database.base.User
import javax.persistence.*
import ru.mmishak.bicyclewalksspring.model.database.base.Entity as DbEntity

@Entity
data class Leader(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,
    override val login: String,
    override val email: String,
    override val password: String,
    override val name: String,
    @OneToMany(mappedBy = "leader")
    val bicycleWalks: MutableList<BicycleWalk> = mutableListOf()
) : DbEntity, User