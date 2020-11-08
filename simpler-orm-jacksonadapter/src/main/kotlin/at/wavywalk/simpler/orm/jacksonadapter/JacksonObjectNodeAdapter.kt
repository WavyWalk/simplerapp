package at.wavywalk.simpler.orm.jacksonadapter

import com.fasterxml.jackson.databind.node.ObjectNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode
import java.time.LocalDateTime

class JacksonObjectNodeAdapter(val objectNode: ObjectNode):
    IObjectNode
{
    override fun set(key: String, node: IObjectNode) {
        objectNode.set(key, (node as JacksonObjectNodeAdapter).objectNode)
    }

    override fun set(key: String, arrayNode: IArrayNode) {
        objectNode.set(key, (arrayNode as JacksonArrayNodeAdapter).arrayNode)
    }

    override fun set(key: String, value: Int?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: Double?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: Long?) {
        if (value == null) {
            objectNode.set(key, value)

        } else {
            objectNode.put(key, value)
        }
    }

    override fun set(key: String, value: Nothing?) {
        objectNode.set(key, null)
    }

    override fun set(key: String, value: Boolean?) {
        objectNode.put(key, value)
    }

    override fun set(key: String, value: String?) {
        if (value == null) {
            objectNode.set(key, null)
        } else {
            objectNode.put(key, value)
        }
    }

    override fun toString(): String {
        return objectNode.toString()
    }

    override fun set(key: String, value: LocalDateTime?) {
        if (value == null) {
            objectNode.set(key, null)
            return
        }
        objectNode.put(key, value.toString())
    }
}