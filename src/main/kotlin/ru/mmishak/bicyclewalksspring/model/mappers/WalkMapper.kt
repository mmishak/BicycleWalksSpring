package ru.mmishak.bicyclewalksspring.model.mappers

import ru.mmishak.bicyclewalksspring.extentions.getOrNull
import ru.mmishak.bicyclewalksspring.model.database.BicycleWalk
import ru.mmishak.bicyclewalksspring.model.database.Cyclist
import ru.mmishak.bicyclewalksspring.model.repository.CyclistsRepository
import ru.mmishak.bicyclewalksspring.model.repository.LeadersRepository
import ru.mmishak.bicyclewalksspring.model.repository.OrganizersRepository
import ru.mmishak.bicyclewalksspring.model.api.BicycleWalk as ApiWalk

class WalkMapper(
    private val organizers: OrganizersRepository,
    private val cyclists: CyclistsRepository,
    private val leaders: LeadersRepository
) {
    fun toModel(data: ApiWalk) = BicycleWalk(
        title = data.title,
        description = data.description,
        duration = data.duration,
        distance = data.distance,
        date = data.date,
        price = data.price,
        organizer = organizers.findById(data.organizerId).getOrNull()
            ?: throw NoSuchElementException("Organizer not found."),
        cyclists = cyclists.findAllById(data.cyclistsIds).toMutableList(),
        leader = data.leaderId?.let { if (leaders.existsById(it)) leaders.findById(it).get() else null },
        leaderStatus = data.leaderStatus
    )

    fun toApi(data: BicycleWalk) = ApiWalk(
        id = data.id,
        title = data.title,
        description = data.description,
        duration = data.duration,
        distance = data.distance,
        date = data.date,
        price = data.price,
        organizerId = data.organizer.id,
        cyclistsIds = data.cyclists.map(Cyclist::id),
        leaderId = data.leader?.id,
        leaderStatus = data.leaderStatus
    )
}