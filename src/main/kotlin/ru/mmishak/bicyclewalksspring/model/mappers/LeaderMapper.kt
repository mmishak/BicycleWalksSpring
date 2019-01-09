package ru.mmishak.bicyclewalksspring.model.mappers

import ru.mmishak.bicyclewalksspring.extentions.getOrNull
import ru.mmishak.bicyclewalksspring.model.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.model.database.Leader
import ru.mmishak.bicyclewalksspring.model.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.model.api.Leader as ApiLeader

class LeaderMapper(private val leaders: LeadersRepository, private val walks: WalksRepository) {
    fun toModel(data: ApiLeader) = Leader(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: leaders.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun toApi(data: Leader) = ApiLeader(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}