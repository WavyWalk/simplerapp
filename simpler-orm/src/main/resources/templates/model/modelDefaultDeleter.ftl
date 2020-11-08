<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
object ${modelClass}DefaultDeleter {

    fun delete(model: ${modelClass}, dslContext: DSLContext = SimplerOrmDependenciesManager.provider.defaultDslContext) {
        dslContext.deleteFrom(${jooqTableInstance})
            .where(${jooqTableInstance}.${primaryKey.tableFieldName}.eq(model.${primaryKey.property}))
            .execute()

        model.${primaryKey.property} = null
    }

}
