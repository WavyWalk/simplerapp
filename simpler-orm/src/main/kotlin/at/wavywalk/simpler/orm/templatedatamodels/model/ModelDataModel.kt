package at.wavywalk.simpler.orm.templatedatamodels.model

import java.lang.Exception
import javax.lang.model.element.TypeElement

class ModelDataModel(
    val typeElement: TypeElement,
    val modelClass: String,
    val fieldBeans: MutableList<FieldBean>,
    val primaryKey: FieldBean,
    val jooqTableInstance: String,
    val fieldBeansExceptPrimaryKey: MutableList<FieldBean>,
    val packagesBean: PackagesBean,
    val hasTimestamps: Boolean?,
    val jooqTableSimpleName: String,
    val shouldGenerateRequestParamsWrapper: Boolean,
    val delegateFromRecordProperties: MutableList<DelegateFromRecordPropertyBean> = mutableListOf()
) {
    lateinit var associationBeans: MutableList<AssociationBean>
    var serializeOnlyFields: MutableList<SerializableOnlyFieldBean> = mutableListOf()
    val modelClassDecapitalized = Character.toLowerCase(modelClass[0]) + modelClass.substring(1);
    val jooqRecordClass: String = jooqTableInstance.let {
        jooqTableSimpleName + "Record"
    }

    fun getNonModelSerializeOnlyFields(): MutableList<SerializableOnlyFieldBean> {
        return serializeOnlyFields.filter { it.thatModelDataModel == null }.toMutableList()
    }

    fun getModelSerializeOnlyFields(): MutableList<SerializableOnlyFieldBean> {
        return serializeOnlyFields.filter { it.thatModelDataModel != null }.toMutableList()
    }

    val paramGetterTypesMap = mutableMapOf<String, String>(
        "Long" to "long",
        "Int" to "int",
        "String" to "string",
        "Timestamp" to "timestamp",
        "MutableList<Int>" to "intList",
        "MutableList<String>" to "stringList",
        "File" to "tempFile",
        "LocalDateTime" to "localDateTime",
        "Double" to "double",
        "Boolean" to "boolean"
    )

    fun getIParamGetterTypeForType(typeToMap: String): String {
        val typeToMap = typeToMap.removeSuffix("?")
        var getter = paramGetterTypesMap.get(typeToMap)
        if (getter == null) {
            throw Exception("No type found for type: ${typeToMap}")
        }
        return getter
    }

}


