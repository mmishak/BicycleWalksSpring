package ru.mmishak.bicyclewalksspring.entity.mappers

import ru.mmishak.bicyclewalksspring.entity.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.entity.database.Cyclist
import ru.mmishak.bicyclewalksspring.exceptions.getOrNull
import ru.mmishak.bicyclewalksspring.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.entity.api.Cyclist as ApiCyclist

class CyclistMapper(private val cyclists: CyclistsRepository, private val walks: WalksRepository) {
    fun transform(data: ApiCyclist) = Cyclist(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: cyclists.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun transform(data: Cyclist) = ApiCyclist(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}