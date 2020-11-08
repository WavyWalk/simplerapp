<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import org.jooq.DSLContext
import at.wavywalk.simpler.orm.modelutils.RepositoryDbUtils
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.model}
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

object ${modelClass}DefaultCreator {

    fun create(model: ${modelClass}, dslContext: DSLContext = SimplerOrmDependenciesManager.provider.defaultDslContext): ${modelClass}{

    <#if hasTimestamps>
        model.createdAt = LocalDateTime.now()
        model.updatedAt = model.createdAt
    </#if>

        val resultSet = dslContext
                .insertInto(${jooqTableInstance})
                .columns(
                <#list fieldBeansExceptPrimaryKey as fieldBean>
                    ${jooqTableInstance}.${fieldBean.tableFieldName}<#sep>,</#sep>
                </#list>
                )
                .values(
                <#list fieldBeansExceptPrimaryKey as fieldBean>
                   model.${fieldBean.property}<#sep>,</#sep>
                </#list>
                )
                .returning(
                    ${jooqTableInstance}.${primaryKey.tableFieldName}
                )
                .fetch().intoResultSet()

        resultSet.next()

        RepositoryDbUtils.executeThisBlockAndCloseResultSet(resultSet) {
            val ${primaryKey.property} = resultSet.get${primaryKey.nonNullableType}(1)
            model.${primaryKey.property} = ${primaryKey.property}
        }

        return model
    }

}
