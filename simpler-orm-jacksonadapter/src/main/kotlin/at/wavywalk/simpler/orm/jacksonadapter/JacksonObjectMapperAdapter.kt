package at.wavywalk.simpler.orm.jacksonadapter

import com.fasterxml.jackson.databind.ObjectMapper
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IToJsonSerializerObjectMapperAdapter


class JacksonObjectMapperAdapter :
    IToJsonSerializerObjectMapperAdapter {
    companion object {
        val objectMapper = ObjectMapper()
    }

    override fun createArrayNode(): IArrayNode {
        return JacksonArrayNodeAdapter(objectMapper.createArrayNode())
    }

    override fun createObjectNode(): IObjectNode {
        return JacksonObjectNodeAdapter(objectMapper.createObjectNode())
    }
}

