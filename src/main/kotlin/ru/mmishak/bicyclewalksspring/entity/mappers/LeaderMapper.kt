package ru.mmishak.bicyclewalksspring.entity.mappers

import ru.mmishak.bicyclewalksspring.entity.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.entity.database.Leader
import ru.mmishak.bicyclewalksspring.exceptions.getOrNull
import ru.mmishak.bicyclewalksspring.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.entity.api.Leader as ApiLeader

class LeaderMapper(private val leaders: LeadersRepository, private val walks: WalksRepository) {
    fun transform(data: ApiLeader) = Leader(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: leaders.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun transform(data: Leader) = ApiLeader(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}