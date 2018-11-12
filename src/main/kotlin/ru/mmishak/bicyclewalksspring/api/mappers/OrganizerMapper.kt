package ru.mmishak.bicyclewalksspring.api.mappers

import ru.mmishak.bicyclewalksspring.entity.BicycleWalk
import ru.mmishak.bicyclewalksspring.entity.Organizer
import ru.mmishak.bicyclewalksspring.exceptions.getOrNull
import ru.mmishak.bicyclewalksspring.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.repository.WalksRepository
import ru.mmishak.bicyclewalksspring.api.entity.Organizer as ApiOrganizer

class OrganizerMapper(private val organizers: OrganizersRepository, private val walks: WalksRepository) {
    fun transform(data: ApiOrganizer) = Organizer(
        id = data.id,
        login = data.login,
        email = data.email,
        password = data.password ?: organizers.findById(data.id).getOrNull()?.password
        ?: throw Exception("Password not found."),
        name = data.name,
        bicycleWalks = walks.findAllById(data.bicycleWalksIds).toMutableList()
    )

    fun transform(data: Organizer) = ApiOrganizer(
        id = data.id,
        login = data.login,
        email = data.email,
        name = data.name,
        bicycleWalksIds = data.bicycleWalks.map(BicycleWalk::id)
    )
}