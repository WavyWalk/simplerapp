package at.wavywalk.simpler.orm.templatedatamodels.model

import at.wavywalk.simpler.orm.annotations.SerializableOnly
import at.wavywalk.simpler.orm.filegeneration.AggregateModelsBank
import org.jetbrains.annotations.Nullable
import java.lang.Exception
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

object SerializableOnlyFieldBeanFactory {

    fun create(element: Element): MutableList<SerializableOnlyFieldBean> {
        val serializableOnlyFields = mutableListOf<SerializableOnlyFieldBean>()

        element.enclosedElements.forEach {enclosedElement ->
            ifIsSerializableOnlyField(enclosedElement) {
                serializableOnlyAnnotation ->

                serializableOnlyFields.add(
                    produceSerializedOnlyFieldBean(serializableOnlyAnnotation, enclosedElement)
                )
            }
        }

        return serializableOnlyFields
    }

    private inline fun ifIsSerializableOnlyField(element: Element, block: (serializableOnly: SerializableOnly) -> Unit) {
        if(element.kind == ElementKind.FIELD){
            val fieldAnnotation: SerializableOnly? = element.getAnnotation(SerializableOnly::class.java)
            if (fieldAnnotation != null) {
                block.invoke(fieldAnnotation)
            }
        }
    }

    private fun produceSerializedOnlyFieldBean(
        serializedOnlyAnnotation: SerializableOnly,
        element: Element
    ): SerializableOnlyFieldBean {

        var thatModelDataModel: ModelDataModel? = null
        val property: String = element.simpleName.toString()
        val type: String = counstructType(element)
        val isMutableList = isMutableList(type)
        if (isMutableList) {
            val otherModelSimpleType = getOtherModelSimpleTypeFromMutableListType(type)
            thatModelDataModel = AggregateModelsBank.models[otherModelSimpleType]
        } else {
            val otherModelSimpleType = getOtherModelSimpleType(type)
            thatModelDataModel = AggregateModelsBank.models[otherModelSimpleType]
        }

        val nonNullableType = constructNonNullableType(type)

        return SerializableOnlyFieldBean(
            element = element,
            property = property,
            type = type,
            nonNullableType = nonNullableType,
            thatModelDataModel = thatModelDataModel,
            isCollectionOfModel = isMutableList
        )
    }

    private fun getOtherModelSimpleType(type: String): String {
        return type.removeSuffix('?'.toString());
    }

    private fun getOtherModelSimpleTypeFromMutableListType(type: String): String {
        val opening = type.indexOfFirst { it == '<' }
        val closing = type.indexOfLast { it == '>' }
        if (opening < 0 || closing < 0) {
            throw IllegalStateException("#getOtherModelSimpleType for type: ${type}")
        }
        return type.substring(opening+1..closing-1).split('.').last()
    }

    private fun isMutableList(type: String): Boolean {
        return type.startsWith("mutableList")
    }

    private fun counstructType(element: Element): String {
        var typeName = element.asType().toString()
        if (isJavaList(typeName)) {
            typeName = typeName.replace("java.util.List", "mutableList")
        } else {
            typeName = typeName.split('.').last()
        }
        typeName = convertBasTypeNameIfNecessary(typeName)
        if (isNullableProperty(element)) {
            typeName += "?"
        }
        return typeName
    }

    private fun isJavaList(qualifiedName: String): Boolean {
        return qualifiedName.startsWith("java.util.List")
    }

    private fun convertBasTypeNameIfNecessary(typeName: String): String {
        when (typeName) {
            "Integer" -> {
                return "Int"
            }
            else -> {
                return typeName
            }
        }
    }

    private fun isNullableProperty(element: Element): Boolean {
        val nullableAnnotation = element.getAnnotation(Nullable::class.java)
        return nullableAnnotation == null
    }

    private fun constructNonNullableType(type: String): String {
        val kotlinQuestionMark = type[type.length - 1]
        if (kotlinQuestionMark == '?'){
            return type.substring(0, type.length - 1)
        } else {
            return type
        }
    }

}