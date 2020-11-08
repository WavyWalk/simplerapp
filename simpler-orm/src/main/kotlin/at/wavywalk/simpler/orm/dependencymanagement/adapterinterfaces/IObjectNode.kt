package at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces

import java.time.LocalDateTime

interface IObjectNode {

    fun set(key: String, arrayNode: IArrayNode)

    fun set(key: String, node: IObjectNode)

    fun set(key: String, value: String?)

    fun set(key: String, value: Boolean?)

    fun set(key: String, value: Int?)

    fun set(key: String, value: Long?)

    fun set(key: String, value: Nothing?)

    fun set(key: String, value: Double?)

    fun set(key: String, value: LocalDateTime?)

    override fun toString(): String

}


