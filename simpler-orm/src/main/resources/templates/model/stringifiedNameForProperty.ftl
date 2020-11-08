<#-- @ftlvariable name="" type="at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel" -->
<#-- @ftlvariable name="ab" type="at.wavywalk.simpler.orm.templatedatamodels.model.AssociationBean" -->
package ${packagesBean.baseGenerated}


object ${modelClass}StringifiedNameForProperty {

    <#list fieldBeans as fieldBean>
    const val ${fieldBean.property}: String = "${fieldBean.property}"
    </#list>
    <#list associationBeans as ab>
    const val ${ab.propertyName}: String = "${ab.propertyName}"
    </#list>

}