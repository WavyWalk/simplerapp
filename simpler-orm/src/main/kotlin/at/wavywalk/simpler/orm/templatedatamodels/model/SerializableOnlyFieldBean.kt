package at.wavywalk.simpler.orm.templatedatamodels.model

import javax.lang.model.element.Element

class SerializableOnlyFieldBean
constructor(
    val element: Element,
    val property: String,
    val type: String,
    val nonNullableType: String,
    var thatModelDataModel: ModelDataModel?,
    var isCollectionOfModel: Boolean
)
{
    var capitalizedProperty: String
    val decapitalizedType: String
    val decapitalizedNonNullableType: String
    init {
        capitalizedProperty = property.first().toUpperCase() + property.substring(1, property.length)
        decapitalizedType = type.first().toLowerCase() + type.substring(1, type.length)
        decapitalizedNonNullableType = nonNullableType.first().toLowerCase() + nonNullableType.substring(1, nonNullableType.length)
    }
}