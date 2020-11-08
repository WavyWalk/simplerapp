<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import at.wavywalk.simpler.orm.services.ModelInvalidError
import at.wavywalk.simpler.orm.services.SingleMergeResultTriple
import at.wavywalk.simpler.orm.services.ListMergeResultTriple
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


class ${modelClass}Merger(val model: ${modelClass}, val modelToMerge: ${modelClass}) {

    class WhichPropertiesToMerge(val default: Boolean) {
    <#list fieldBeans as fieldBean>
        var ${fieldBean.property}: Boolean = default
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property}: Boolean = default
    </#list>
    }
    
    class WhichPropertiesToMergeOnlyMode(var whichPropertiesToMerge: WhichPropertiesToMerge) {
    
    <#list fieldBeans as fieldBean>
        var ${fieldBean.property} = true
        get() {
            whichPropertiesToMerge.${fieldBean.property} = true
            return field
        }
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property} = true
        get() {
            whichPropertiesToMerge.${serializeOnlyField.property} = true
            return field
        }
    </#list>
    }
    
    class WhichPropertiesToMergeExceptMode(var whichPropertiesToMerge: WhichPropertiesToMerge) {
    <#list fieldBeans as fieldBean>
        var ${fieldBean.property} = false
        get() {
            whichPropertiesToMerge.${fieldBean.property} = false
            return field
        }
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        var ${serializeOnlyField.property} = false
        get() {
            whichPropertiesToMerge.${serializeOnlyField.property} = false
            return field
        }
    </#list>
    }

    companion object {
        fun merge(
            existing: MutableList<${modelClass}>, toMerge: MutableList<${modelClass}>,
            block: ((${modelClass}Merger)->Unit)? = null
        ): ListMergeResultTriple<${modelClass}> {
            val merged = mutableListOf<${modelClass}>()
            val removed = mutableListOf<${modelClass}>()
            val added = mutableListOf<${modelClass}>()

            val result = mutableListOf<${modelClass}>()

            val modelListToMerge = toMerge ?: mutableListOf<${modelClass}>()
            val existingModelList = existing ?: mutableListOf<${modelClass}>()
            val existingIdMap = ${modelClass}Utils.buildIdTo${modelClass}Map(existingModelList)
            val mergeIdMap = ${modelClass}Utils.buildIdTo${modelClass}Map(modelListToMerge)
            mergeIdMap.forEach { (id, ${modelClassDecapitalized}) ->
                ${modelClassDecapitalized}.forEach { toMergeWith ->
                    val existing = existingIdMap[id]?.firstOrNull()
                    if (existing == null) {
                        added.add(toMergeWith)
                        result.add(toMergeWith)
                    } else {
                        result.add(existing)
                        val merger = ${modelClass}Merger(existing, toMergeWith)
                        block?.invoke(merger)
                        merger.merge()
                        merged.add(existing)
                    }
                }
            }
            existingModelList.forEach {
                if (mergeIdMap[it.id] == null) {
                    removed.add(it)
                }
            }
            return ListMergeResultTriple(merged, added, removed)
        }
    }
    
    inline fun only(block: (WhichPropertiesToMergeOnlyMode) -> Unit) {
        whichPropertiesToMerge = WhichPropertiesToMergeOnlyMode(WhichPropertiesToMerge(false)).let {
            it.apply(block)
            it.whichPropertiesToMerge
        }
    }
    
    inline fun except(block: (WhichPropertiesToMergeExceptMode) -> Unit) {
        whichPropertiesToMerge = WhichPropertiesToMergeExceptMode(WhichPropertiesToMerge(true)).let {
            it.apply(block)
            it.whichPropertiesToMerge
        }
    }
    
    var whichPropertiesToMerge: WhichPropertiesToMerge = WhichPropertiesToMerge(true)


    fun merge() {
    <#list fieldBeans as fieldBean>
        if (whichPropertiesToMerge.${fieldBean.property}) {
            model.record.${fieldBean.property} = modelToMerge.${fieldBean.property}
        }
    </#list>
    <#list getNonModelSerializeOnlyFields() as serializeOnlyField>
        model.${serializeOnlyField.property} = modelToMerge.${serializeOnlyField.property}
    </#list>
    }

    <#list associationBeans as ab>

    <#if ab.associationType == "HAS_ONE" || ab.associationType == "BELONGS_TO" || ab.associationType == "HAS_ONE_AS_POLYMORPHIC">
        fun merge${ab.capitalizedPropertyName}(
            block: ((${ab.associatedModelDataModel.modelClass}Merger)->Unit)? = null
        ): SingleMergeResultTriple<${ab.associatedModelDataModel.modelClass}> {
            val thisModel = model.record.${ab.propertyName}
            val thatModel = modelToMerge.record.${ab.propertyName}
            if (thisModel == null && thatModel == null) {
                return SingleMergeResultTriple(null, null, null)
            }
            if (thisModel == null && thatModel != null) {
                model.record.${ab.propertyName} = thatModel
                return SingleMergeResultTriple(null, thatModel, null)
            }
            if (thisModel != null && thatModel == null) {
                val removed = model.record.${ab.propertyName}
                model.record.${ab.propertyName} = null
                return SingleMergeResultTriple(null, null, removed)
            }
            val merger = ${ab.associatedModelDataModel.modelClass}Merger(thisModel!!, thatModel!!)
            block?.invoke(merger)
            merger.merge()
            return SingleMergeResultTriple(thisModel, null, null)
        }
    </#if>

    <#if ab.associationType == "HAS_MANY">
    fun merge${ab.capitalizedPropertyName}(
        block: ((${ab.associatedModelDataModel.modelClass}Merger)->Unit)? = null
    ): ListMergeResultTriple<${ab.associatedModelDataModel.modelClass}> {
        val merged = mutableListOf<${ab.associatedModelDataModel.modelClass}>()
        val removed = mutableListOf<${ab.associatedModelDataModel.modelClass}>()
        val added = mutableListOf<${ab.associatedModelDataModel.modelClass}>()
        
        val result = mutableListOf<${ab.associatedModelDataModel.modelClass}>()

        val modelListToMerge = modelToMerge.${ab.propertyName} ?: mutableListOf()
        val existingModelList = model.${ab.propertyName} ?: mutableListOf()
        val existingIdMap = ${ab.associatedModelDataModel.modelClass}Utils.buildIdTo${ab.associatedModelDataModel.modelClass}Map(existingModelList)
        val mergeIdMap = ${ab.associatedModelDataModel.modelClass}Utils.buildIdTo${ab.associatedModelDataModel.modelClass}Map(modelListToMerge)
        mergeIdMap.forEach { (id, ${ab.propertyName}) ->
            ${ab.propertyName}.forEach { toMergeWith ->
                val existing = existingIdMap[id]?.firstOrNull()
                if (existing == null) {
                    added.add(toMergeWith)
                    result.add(toMergeWith)
                } else {
                    result.add(existing)
                    val merger = ${ab.associatedModelDataModel.modelClass}Merger(existing, toMergeWith)
                    block?.invoke(merger)
                    merger.merge()
                    merged.add(existing)
                }
            }
        }
        existingModelList.forEach {
            if (mergeIdMap[it.id] == null) {
                removed.add(it)
            }
        }
        model.record.${ab.propertyName} = result
        return ListMergeResultTriple(merged, added, removed)
    }
    </#if>
    </#list>

    <#list getModelSerializeOnlyFields() as serializeOnlyField>
        <#if serializeOnlyField.isCollectionOfModel()>
    fun merge${serializeOnlyField.capitalizedProperty}(
        block: ((${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}Merger)->Unit)? = null
    ): ListMergeResultTriple<${serializeOnlyField.thatModelDataModel.packagesBean.model}> {
        val merged = mutableListOf<${serializeOnlyField.thatModelDataModel.packagesBean.model}>()
        val removed = mutableListOf<${serializeOnlyField.thatModelDataModel.packagesBean.model}>()
        val added = mutableListOf<${serializeOnlyField.thatModelDataModel.packagesBean.model}>()

        val result = mutableListOf<${serializeOnlyField.thatModelDataModel.packagesBean.model}>()

        val modelListToMerge = modelToMerge.${serializeOnlyField.property} ?: mutableListOf()
        val existingModelList = model.${serializeOnlyField.property} ?: mutableListOf()
        val existingIdMap = ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}Utils.buildIdTo${serializeOnlyField.thatModelDataModel.modelClass}Map(existingModelList)
        val mergeIdMap = ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}Utils.buildIdTo${serializeOnlyField.thatModelDataModel.modelClass}Map(modelListToMerge)
        mergeIdMap.forEach { (id, ${serializeOnlyField.property}) ->
            ${serializeOnlyField.property}.forEach { toMergeWith ->
                val existing = existingIdMap[id]?.firstOrNull()
                if (existing == null) {
                    added.add(toMergeWith)
                    result.add(toMergeWith)
                } else {
                    result.add(existing)
                    val merger = ${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}Merger(existing, toMergeWith)
                    block?.invoke(merger)
                    merger.merge()
                    merged.add(existing)
                }
            }
        }
        existingModelList.forEach {
            if (mergeIdMap[it.id] == null) {
                removed.add(it)
            }
        }
        model.${serializeOnlyField.property} = result
        return ListMergeResultTriple(merged, added, removed)
    }

        <#else>
    fun merge${serializeOnlyField.capitalizedProperty}(
        block: ((${serializeOnlyField.thatModelDataModel.packagesBean.baseGenerated}.${serializeOnlyField.thatModelDataModel.modelClass}Merger)->Unit)? = null
    ): SingleMergeResultTriple<${serializeOnlyField.thatModelDataModel.modelClass}> {
        val thisModel = model.record.${serializeOnlyField.property}
        val thatModel = modelToMerge.record.${serializeOnlyField.property}
        if (thisModel == null && thatModel == null) {
            return SingleMergeResultTripe(null, null, null)
        }
        if (thisModel == null && thatModel != null {
            model.record.${serializeOnlyField.property} = thatModel
            return SingleMergeResultTriple(null, thatModel, null)
        }
        val merger = ${serializeOnlyField.thatModelDataModel.modelClass}Merger(thisModel, thatModel)
        block?.invoke(merger)
        merger.merge()
        return SingleMergeResultTriple(null, null, null)
    }
        </#if>
    </#list>

}