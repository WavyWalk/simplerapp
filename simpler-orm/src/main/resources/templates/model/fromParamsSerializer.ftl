<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import at.wavywalk.simpler.orm.services.ModelInvalidError
import org.jooq.SelectQuery
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.TableLike
import org.jooq.SelectField
import java.sql.ResultSet
import ${packagesBean.jooqGeneratedTable}.*
import ${packagesBean.jooqGeneratedTable}
import ${packagesBean.jooqTablesRoot}.records.${jooqRecordClass}
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
    import ${fieldType}
</#list>
<#list packagesBean.associatedModelTypesToImport as typeToImport>
    import ${typeToImport}
</#list>
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
import at.wavywalk.simpler.utils.requestparameters.IParam

class ${modelClass}FromParamsSerializer {

    var block: ((IParam, ${modelClass}) -> Unit)? = null

    class WhichPropertiesToSerialize(val default: Boolean) {
    <#list fieldBeans as fieldBean>
        var ${fieldBean.property}: Boolean = default
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property}: Boolean = default
    </#list>
    }

    class WhichPropertiesToSerializeOnlyMode(val whichPropertiesToSerialize: WhichPropertiesToSerialize) {
        <#list fieldBeans as fieldBean>
        var ${fieldBean.property} = true
            get() {
                whichPropertiesToSerialize.${fieldBean.property} = true
                return field
            }
        </#list>
        <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property} = true
            get() {
                whichPropertiesToSerialize.${serializeOnlyField.property} = true
                return field
            }
        </#list>
    }

    class WhichPropertiesToSerializeExceptMode(val whichPropertiesToSerialize: WhichPropertiesToSerialize) {
        <#list fieldBeans as fieldBean>
        var ${fieldBean.property} = false
            get() {
                whichPropertiesToSerialize.${fieldBean.property} = false
                return field
            }
        </#list>
        <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property} = false
            get() {
                whichPropertiesToSerialize.${serializeOnlyField.property} = false
                return field
            }
        </#list>
    }

    inline fun only(block: (WhichPropertiesToSerializeOnlyMode) -> Unit) {
        whichPropertiesToSerialize = WhichPropertiesToSerializeOnlyMode(WhichPropertiesToSerialize(false)).let {
            it.apply(block)
            it.whichPropertiesToSerialize
        }
    }

    inline fun except(block: (WhichPropertiesToSerializeExceptMode) -> Unit) {
        whichPropertiesToSerialize = WhichPropertiesToSerializeExceptMode(WhichPropertiesToSerialize(true)).let {
            it.apply(block)
            it.whichPropertiesToSerialize
        }
    }

    var whichPropertiesToSerialize: WhichPropertiesToSerialize = WhichPropertiesToSerialize(true)

    class WhichAssociatedToSerialize() {

    <#list associationBeans as ab>
        var ${ab.propertyName}: Boolean = false
    </#list>

    <#list getModelSerializeOnlyFields() as serializeField>
        var ${serializeField.property}: Boolean = false
    </#list>
    }

    var whichAssociatedToSerialize = WhichAssociatedToSerialize()

    class AssociatedSerializers {
    <#list associationBeans as ab>
        var ${ab.propertyName}: ${ab.associatedModelDataModel.modelClass}FromParamsSerializer? = null
    </#list>

    <#list getModelSerializeOnlyFields() as serializeField>
        var ${serializeField.property}: ${serializeField.thatModelDataModel.packagesBean.baseGenerated}.${serializeField.thatModelDataModel.modelClass}FromParamsSerializer? = null
    </#list>
    }

    var associatedSerializers = AssociatedSerializers()

    <#list associationBeans as ab>
    fun include${ab.capitalizedPropertyName}(lambda: ((${ab.associatedModelDataModel.modelClass}FromParamsSerializer) -> Unit)?) {
        whichAssociatedToSerialize.${ab.propertyName} = true
        val thatSerializer = ${ab.associatedModelDataModel.modelClass}FromParamsSerializer()
        if (lambda != null) {
            lambda.invoke(thatSerializer)
        }
        this.associatedSerializers.${ab.propertyName} = thatSerializer
    }
    </#list>

    <#list getModelSerializeOnlyFields() as serializeField>
    fun include${serializeField.capitalizedProperty}(lambda: ((${serializeField.thatModelDataModel.packagesBean.baseGenerated}.${serializeField.thatModelDataModel.modelClass}FromParamsSerializer)->Unit)?) {
        whichAssociatedToSerialize.${serializeField.property} = true
        val thatSerializer = ${serializeField.thatModelDataModel.packagesBean.baseGenerated}.${serializeField.thatModelDataModel.modelClass}FromParamsSerializer()
        if (lambda != null) {
            lambda.invoke(thatSerializer)
        }
        this.associatedSerializers.${serializeField.property} = thatSerializer
    }
    </#list>

    fun serialize(params: IParam?): ${modelClass}? {
        if (params == null) {
            return null
        }
        val model = ${modelClass}()

        block?.invoke(params, model)

        <#list fieldBeans as fieldBean>
        if (whichPropertiesToSerialize.${fieldBean.property}) {
            model.record.${fieldBean.property} = params.get("${fieldBean.property}")?.${getIParamGetterTypeForType(fieldBean.type)}
        }
        </#list>

        <#list associationBeans as ab>
            <#if ab.associationType == "HAS_ONE" || ab.associationType == "BELONGS_TO">
        if (whichAssociatedToSerialize.${ab.propertyName}) {
            model.record.${ab.propertyName} = params.get("${ab.propertyName}")?.let {
                associatedSerializers.${ab.propertyName}!!.serialize(it)
            }
        }
            </#if>
        </#list>

        <#list associationBeans as ab>
            <#if ab.associationType == "HAS_MANY">
        if (whichAssociatedToSerialize.${ab.propertyName}) {
            model.record.${ab.propertyName} = params.get("${ab.propertyName}")?.let {
                associatedSerializers.${ab.propertyName}!!.serializeCollection(it)
            }
        }
            </#if>
        </#list>

        <#list modelSerializeOnlyFields as serializeField >
            <#if serializeField.isCollectionOfModel()>
        if (whichAssociatedToSerialize.${serializeField.property}) {
            model.${serializeField.property} = params.get("${serializeField.property}")?.let {
                associatedSerializers.${serializeField.property}!!.serializeCollection(it)
            }
        }
            <#else>
        if (whichAssociatedToSerialize.${serializeField.property}) {
            model.${serializeField.property} = params.get("${serializeField.property}")?.let {
                associatedSerializers.${serializeField.property}!!.serialize(it)
            }
        }
            </#if>
        </#list>

        return model
    }

    fun serializeCollection(params: IParam?): MutableList<${modelClass}>? {
        val nodes = params?.nodeList()
        if (nodes == null) {
            return null
        }
        return nodes.mapTo(mutableListOf<${modelClass}>()) { serialize(it)!! }
    }

}