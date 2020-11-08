package serialization

import at.wavywalk.simpler.dependencymanagement.SimplerDependencyManager
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesProvider
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode
import at.wavywalk.simpler.utils.requestparameters.IParam
import at.wavywalk.simplerjacksonadapter.JacksonParametersParser
import com.fasterxml.jackson.databind.ObjectMapper

object Serialization {

    var parser = JacksonParametersParser(ObjectMapper())

    fun paramsFromJson(toParse: String?): IParam? {
        if (toParse == null) {
            return null
        }
        return parser.parse(toParse)
    }

    fun createObject(): IObjectNode {
        return SimplerOrmDependenciesManager.provider.jsonObjectMapper.createObjectNode()
    }

    fun createArray(): IArrayNode {
        return SimplerOrmDependenciesManager.provider.jsonObjectMapper.createArrayNode()
    }

}