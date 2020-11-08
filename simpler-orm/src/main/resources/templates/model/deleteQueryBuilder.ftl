<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import org.jooq.DeleteQuery
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.TableLike
import org.jooq.SelectField
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager

<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

open class ${modelClass}DeleteQueryBuilder
constructor(
    val model: ${modelClass},
    dslContext: DSLContext,
    var tableOrAlias: ${jooqTableSimpleName}
) {
    constructor(model: ${modelClass}): this(model, SimplerOrmDependenciesManager.provider.defaultDslContext, ${jooqTableInstance})
    constructor(model: ${modelClass}, tableOrAlias: ${jooqTableSimpleName}): this(model, SimplerOrmDependenciesManager.provider.defaultDslContext, tableOrAlias)
    constructor(model: ${modelClass}, dslContext: DSLContext): this(model, dslContext, ${jooqTableInstance})
    val deleteQuery: DeleteQuery<${jooqTableSimpleName}Record>
    init {
        deleteQuery = dslContext.deleteQuery(tableOrAlias)
    }

    fun execute() {
        if (model.${primaryKey.property} == null) {
            throw(Exception("delete failed: no primary key on model"))
        }
        deleteQuery.addConditions(tableOrAlias.${primaryKey.tableFieldName}.eq(model.${primaryKey.property}))
        deleteQuery.execute()
    }

}
