<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}

import ${packagesBean.model}

<#list packagesBean.fieldTypes as fieldType>
    import ${fieldType}
</#list>
<#list packagesBean.associatedModelTypesToImport as typeToImport>
import ${typeToImport}
</#list>
<#list associationBeans as ab>
import ${ab.associatedModelDataModel.packagesBean.baseGenerated}.${ab.associatedModelDataModel.modelClass}Params
</#list>
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
import at.wavywalk.simpler.utils.requestparameters.IParam

class ${modelClass}Params {

    class Whitelist {

        var inBuildMode = true
        <#list fieldBeans as fieldBean>

        var ${fieldBean.property}: Boolean = false
            get() {
            if (inBuildMode) {
                field = true
            }
                return field
            }
        </#list>
    }

    var whitelist: Whitelist
    var requestParams: IParam

    constructor(requestParams: IParam, whitelistBlock: Whitelist.()->Unit) {
        val whitelist = Whitelist()
        whitelist.whitelistBlock()
        whitelist.inBuildMode = false
        this.requestParams = requestParams
        this.whitelist = whitelist
    }

    constructor(requestParams: IParam, whitelist: Whitelist) {
        this.requestParams = requestParams
        this.whitelist = whitelist
    }
    <#list fieldBeans as fieldBean>

    var ${fieldBean.property}: ${fieldBean.nonNullableType}? = null
        get() {
            return requestParams.get("${fieldBean.property}")?.${fieldBean.decapitalizedNonNullableType}
        }
    </#list>

    <#list associationBeans as ab>

    <#if ab.associationType == "HAS_ONE" || ab.associationType == "BELONGS_TO">
    fun ${ab.propertyName}(): IParam? {
        return requestParams.get("${ab.propertyName}")
    }
    <#elseif  ab.associationType == "HAS_MANY">
    fun ${ab.propertyName}(): IParam? {
        return requestParams.get("${ab.propertyName}")?.paramList
    }
    </#if>
    </#list>

    fun update(${modelClassDecapitalized}: ${modelClass}, yieldParams: (IParam)->Unit) {
        ${modelClassDecapitalized}.record.also {
            <#list fieldBeans as fieldBean>

            if (whitelist.${fieldBean.property}) {
                it.${fieldBean.property} = requestParams.get("${fieldBean.property}")?.${fieldBean.decapitalizedNonNullableType}
            }
            </#list>
        }
    }

}