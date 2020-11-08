package at.wavywalk.simpler.orm.jacksonadapter

import com.fasterxml.jackson.databind.node.ArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode

class JacksonArrayNodeAdapter(val arrayNode: ArrayNode):
    IArrayNode {

    override fun add(node: IObjectNode) {
        arrayNode.add((node as JacksonObjectNodeAdapter).objectNode)
    }

    override fun add(value: String?) {
        arrayNode.add(value)
    }

    override fun toString(): String {
        return arrayNode.toString()
    }

    override fun serializeToString(): String {
        return arrayNode.toString()
    }

}