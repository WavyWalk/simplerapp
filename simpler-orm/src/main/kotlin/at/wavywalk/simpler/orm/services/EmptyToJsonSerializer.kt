package at.wavywalk.simpler.orm.services

import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode

class EmptyToJsonSerializer {

    companion object {
        val objectMapper = SimplerOrmDependenciesManager.provider.jsonObjectMapper

        inline fun serialize(block: (EmptyToJsonSerializer)->Unit): IArrayNode {
            val root = objectMapper.createArrayNode()
            block(EmptyToJsonSerializer())
            return root
        }

    }

    val root = objectMapper.createObjectNode()

    fun serializeToNode(): IObjectNode {
        return root
    }

    fun serializeToString(): String {
        return root.toString()
    }

    fun set(key: String, value: String?) {
        root.set(key, value)
    }

    fun set(key: String, value: Int?) {
        root.set(key, value)
    }

    fun set(key: String, value: Long?) {
        root.set(key, value)
    }

    fun set(key: String, value: Boolean) {
        root.set(key, value)
    }

}