package at.wavywalk.simpler.orm.dependencymanagement

import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IToJsonSerializerObjectMapperAdapter
import org.jooq.DSLContext

open class SimplerOrmDependenciesProvider(
    val defaultDslContext: DSLContext,
    val jsonObjectMapper: IToJsonSerializerObjectMapperAdapter
) {


}

