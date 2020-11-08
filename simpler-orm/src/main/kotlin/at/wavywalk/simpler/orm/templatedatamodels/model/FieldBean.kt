package at.wavywalk.simpler.orm.templatedatamodels.model

import javax.lang.model.element.*

class FieldBean
constructor(
        val element: Element,
        val property: String,
        val tableFieldName: String,
        val type: String,
        val isPrimaryKey: Boolean,
        val nonNullableType: String
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
    val actualDbFieldName = tableFieldName.toLowerCase()
}

//class FieldTestBean(element: Element) {
//    val simpleName: String
//    val kind: String
//    val returnType: String
//    val enclElems: String
//    //val rtqn: String
//    init {
//        var enc = ""
//        element.getAnnotation(org.jetbrains.annotations.Nullable::class.java)
//
//        enclElems = element.enclosedElements.size.toString()
//
//        val el = element
//        simpleName = el.simpleName.toString()
//        kind = el.kind.toString()
//        returnType = el.asType().toString()
//
//        //val rt  = el.returnType as TypeElement
//        //rtqn = rt.qualifiedName.toString()
//    }
//
//}