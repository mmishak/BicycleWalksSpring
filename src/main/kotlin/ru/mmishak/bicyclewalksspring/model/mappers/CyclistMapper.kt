package ru.mmishak.bicyclewalksspring.model.mappers

import ru.mmishak.bicyclewalksspring.extentions.getOrNull
import ru.mmishak.bicyclewalksspring.model.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.model.database.Cyclist
import ru.mmishak.bicyclewalksspring.model.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.model.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.model.api.Cyclist as ApiCyclist

class CyclistMapper(private val cyclists: CyclistsRepository, private val walks: WalksRepository) {
    fun toModel(data: ApiCyclist) = Cyclist(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: cyclists.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun toApi(data: Cyclist) = ApiCyclist(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}