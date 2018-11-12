package ru.mmishak.bicyclewalksspring.entity

import ru.mmishak.bicyclewalksspring.entity.base.LeaderStatus
import javax.persistence.*
import ru.mmishak.bicyclewalksspring.entity.base.Entity as DbEntity

@Entity
data class BicycleWalk(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,
    var title: String,
    var description: String,
    var duration: Long,
    var distance: Int,
    var date: Long,
    var price: Int = 0,

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    val organizer: Organizer,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "cyclist_walk",
        joinColumns = [JoinColumn(name = "cyclist_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "walk_id", referencedColumnName = "id")]
    )
    val cyclists: MutableList<Cyclist>,

    @ManyToOne
    @JoinColumn(name = "leader_id")
    var leader: Leader? = null,

    var leaderStatus: LeaderStatus = if (leader == null) LeaderStatus.WITHOUT_LEADER else LeaderStatus.WAITING_ACCEPT
) : DbEntity {
    val isPublished: Boolean
        get() = leaderStatus == LeaderStatus.WITHOUT_LEADER || leaderStatus == LeaderStatus.ACCEPTED
}