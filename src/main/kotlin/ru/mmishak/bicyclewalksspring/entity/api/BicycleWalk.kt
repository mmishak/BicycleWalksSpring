package ru.mmishak.bicyclewalksspring.entity.api

import ru.mmishak.bicyclewalksspring.entity.database.base.LeaderStatus

data class BicycleWalk(
    var id: Long = 0,
    var title: String,
    var description: String,
    var duration: Long,
    var distance: Int,
    var date: Long,
    var price: Int = 0,
    val organizerId: Long,
    val cyclistsIds: List<Long> = listOf(),
    var leaderId: Long? = null,
    var leaderStatus: LeaderStatus = if (leaderId == null) LeaderStatus.WITHOUT_LEADER else LeaderStatus.WAITING_ACCEPT
)