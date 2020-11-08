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
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IArrayNode
import at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces.IObjectNode

class ${modelClass}ToJsonSerializer(val model: ${modelClass}) {
    companion object {
        val objectMapper = SimplerOrmDependenciesManager.provider.jsonObjectMapper
    
        fun serialize(models: MutableList<${modelClass}>): IArrayNode {
            val root = objectMapper.createArrayNode()
            models.forEach {
                root.add(${modelClass}ToJsonSerializer(it).serializeToNode())
            }
            return root
        }

        inline fun serialize(models: MutableList<${modelClass}>, block: (${modelClass}ToJsonSerializer)->Unit): IArrayNode {
            val root = objectMapper.createArrayNode()
            models.forEach {
                val serializer = ${modelClass}ToJsonSerializer(it)
                block.invoke(serializer)
                root.add(serializer.serializeToNode())
            }
            return root
        }

    }

    class WhichPropertiesToSerialize(val default: Boolean) {
    <#list fieldBeans as fieldBean>
        var ${fieldBean.property}: Boolean = default
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property}: Boolean = default
    </#list>
    }

    class WhichPropertiesToSerializeOnlyMode(var whichPropertiesToSerialize: WhichPropertiesToSerialize) {

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

    class WhichPropertiesToSerializeExceptMode(var whichPropertiesToSerialize: WhichPropertiesToSerialize) {
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

    var cascadeErrors = false

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

    var root = objectMapper.createObjectNode()

    fun includeErrors(): ${modelClass}ToJsonSerializer {
        val node = objectMapper.createObjectNode()
        model.record.validationManager.nonNullableErrors().forEach {
            key, value ->
            val arrayNode = objectMapper.createArrayNode()
            value.forEach {
                arrayNode.add(it)
            }
            node.set(key, arrayNode)
        }
        root.set("errors", node)
        return this
    }


    <#list associationBeans as ab>

    <#if ab.associationType == "HAS_ONE" || ab.associationType == "BELONGS_TO" || ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            root.set("${ab.propertyName}",
                ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(it).also {
                    if (cascadeErrors) {
                        it.cascadeErrors = true
                    }
                }.serializeToNode()
            )
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.associatedModelDataModel.modelClass}ToJsonSerializer)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val thatModelSerializer = ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(it)
            if (cascadeErrors) {
                thatModelSerializer.cascadeErrors = true
            }
            block.invoke(thatModelSerializer)
            root.set("${ab.propertyName}", thatModelSerializer.serializeToNode())
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    <#if ab.associationType == "BELONGS_TO_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    root.set("${ab.propertyName}", ${associatedModelDataModel.modelClass}ToJsonSerializer(it).serializeToNode())
                    return this
                }
                </#list>
                else -> {
                    root.set("${ab.propertyName}", null)
                    return this
                }
            }
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    class ${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy() {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}: ${associatedModelDataModel.modelClass}ToJsonSerializer? = null
        </#list>
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            when(it) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    val proxyYielder = ${ab.capitalizedPropertyName}PolymorphicIncludeYielderProxy()
                    val thatModelSerializer = ${associatedModelDataModel.modelClass}ToJsonSerializer(it)
                    proxyYielder.${associatedModelDataModel.modelClassDecapitalized} = thatModelSerializer
                    block.invoke(proxyYielder)
                    root.set("${ab.propertyName}", thatModelSerializer.serializeToNode())
                    return this
                }
                </#list>
                else -> {
                    root.set("${ab.propertyName}", null)
                    return this
                }
            }
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    <#if ab.associationType == "HAS_MANY" || ab.associationType == "HAS_MANY_AS_POLYMORPHIC">
    fun include${ab.capitalizedPropertyName}(): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                arrayNode.add(
                    ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(thatModel).also {
                        if (cascadeErrors) {
                            it.cascadeErrors = true
                        }
                    }.serializeToNode()
                )
            }
            root.set("${ab.propertyName}", arrayNode)
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }

    inline fun include${ab.capitalizedPropertyName}(block: (${ab.associatedModelDataModel.modelClass}ToJsonSerializer)->Unit): ${modelClass}ToJsonSerializer {
        model.${ab.propertyName}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                val thatModelSerializer = ${ab.associatedModelDataModel.modelClass}ToJsonSerializer(thatModel)
                if (cascadeErrors) {
                    thatModelSerializer.cascadeErrors = true
                }
                block.invoke(thatModelSerializer)
                arrayNode.add(thatModelSerializer.serializeToNode())
            }
            root.set("${ab.propertyName}", arrayNode)
            return this
        }
        root.set("${ab.propertyName}", null)
        return this
    }
    </#if>
    </#list>

    <#list getModelSerializeOnlyFields() as serializeOnlyField>
        <#if serializeOnlyField.isCollectionOfModel()>
    fun include${serializeOnlyField.capitalizedProperty}(): ${modelClass}ToJsonSerializer {
        model.${serializeOnlyField.property}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                arrayNode.add(${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer(thatModel).serializeToNode())
            }
            root.set("${serializeOnlyField.property}", arrayNode)
            return this
        }
        root.set("${serializeOnlyField.property}", null)
        return this
    }

    inline fun include${serializeOnlyField.capitalizedProperty}(block: (${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer)->Unit): ${modelClass}ToJsonSerializer {
        model.${serializeOnlyField.property}?.let {
            val arrayNode = objectMapper.createArrayNode()
            it.forEach { thatModel ->
                val thatModelSerializer = ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer(thatModel)
                block.invoke(thatModelSerializer)
                arrayNode.add(thatModelSerializer.serializeToNode())
            }
            root.set("${serializeOnlyField.property}", arrayNode)
            return this
        }
        root.set("${serializeOnlyField.property}", null)
        return this
    }
        <#else>
    fun include${serializeOnlyField.property}(): ${modelClass}ToJsonSerializer {
        model.${serializeOnlyField.property}?.let {
            root.set(
                "${serializeOnlyField.property}",
                ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer(it).serializeToNode()
            )
            return this
        }
        root.set("${serializeOnlyField.property}", null)
        return this
    }

    inline fun include${serializeOnlyField.capitalizedProperty}(
        block: (${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer)->Unit
    ): ${modelClass}ToJsonSerializer {
        model.${serializeOnlyField.property}?.let {
            val thatModelSerializer = ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}ToJsonSerializer(it)
            block.invoke(thatModelSerializer)
            root.set("${serializeOnlyField.property}", thatModelSerializer.serializeToNode())
            return this
        }
        root.set("${serializeOnlyField.property}", null)
        return this
    }
        </#if>
    </#list>

    fun setApplicablePropertiesToNode(){
        whichPropertiesToSerialize.let {
            <#list fieldBeans as fieldBean>
            if (it.${fieldBean.property}) {
                <#if fieldBean.property == "createdAt" || fieldBean.property == "updatedAt" || fieldBean.nonNullableType == "Timestamp">
                root.set("${fieldBean.property}", model.${fieldBean.property}?.toString())
                <#else>
                root.set("${fieldBean.property}", model.${fieldBean.property})
                </#if>
            }
            </#list>
            <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
            if (it.${serializeOnlyField.property}) {
                root.set("${serializeOnlyField.property}", model.${serializeOnlyField.property})
            }
            </#list>
        }
        if (cascadeErrors) {
            includeErrors()
        }
    }

    fun serializeToNode(): IObjectNode{
        setApplicablePropertiesToNode()
        return root
    }

    fun serializeToString(): String {
        setApplicablePropertiesToNode()
        return root.toString()
    }

    fun set(key: String, value: String?) {
        root.set(key, value)
    }

    fun set(key: String, value: Int?) {
        root.set(key, value)
    }

    fun set(key: String, value: Double?) {
        root.set(key, value)
    }

    fun set(key: String, value: Long?) {
        root.set(key, value)
    }

    fun set(key: String, value: Boolean) {
        root.set(key, value)
    }

    fun set(key: String, value: IArrayNode) {
        root.set(key, value)
    }

}
