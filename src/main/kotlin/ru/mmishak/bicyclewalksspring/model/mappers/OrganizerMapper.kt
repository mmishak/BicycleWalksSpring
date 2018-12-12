package ru.mmishak.bicyclewalksspring.model.mappers

import ru.mmishak.bicyclewalksspring.extentions.getOrNull
import ru.mmishak.bicyclewalksspring.model.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.model.database.Organizer
import ru.mmishak.bicyclewalksspring.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.model.api.Organizer as ApiOrganizer

class OrganizerMapper(private val organizers: OrganizersRepository, private val walks: WalksRepository) {
    fun toModel(data: ApiOrganizer) = Organizer(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: organizers.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun toApi(data: Organizer) = ApiOrganizer(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}