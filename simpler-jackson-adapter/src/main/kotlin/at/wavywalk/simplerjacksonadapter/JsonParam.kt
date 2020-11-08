package at.wavywalk.simplerjacksonadapter

import at.wavywalk.simpler.utils.requestparameters.IParam
import at.wavywalk.simpler.utils.requestparameters.ParamTypesEnum
import com.fasterxml.jackson.databind.JsonNode
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class JsonParam(val jsonNode: JsonNode): IParam {

    var listIsCached = false

    override var valueType = ParamTypesEnum.UNSET
    override var value: Any?
        get() {
            return jsonNode
        }
        set(value) {}

    override val intList: MutableList<Int>
        get() {
            if (listIsCached) {
                return this.value as MutableList<Int>
            } else {
                listIsCached = true
                val intList = mutableListOf<Int>()
                jsonNode.forEach {
                    intList.add(it.intValue())
                }
                return intList
            }
        }

    override val timestamp: Timestamp?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            val textValue = jsonNode.textValue()
            textValue ?: return null
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
                val parsedDate = dateFormat.parse(textValue)
                val timestamp = java.sql.Timestamp(parsedDate.time)
                return timestamp
            } catch (error: Exception) {
                println("catched when getting timestamp: ${error.message}")
                return null
            }
        }

    override val double: Double?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.asDouble()
        }

    override val string: String?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.textValue()
        }

    override val boolean: Boolean?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.booleanValue()
        }

    override val long: Long?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.longValue()
        }

    override val int: Int?
        get() {
            if (jsonNode.isNull) {
                return null
            }
            return jsonNode.intValue()
        }

    override val localDateTime: LocalDateTime?
        get() {
            jsonNode.textValue()?.let {
                return LocalDateTime.parse(it as String)
            } ?: return null
        }

    override val stringList: MutableList<String>
        get() {
            if (listIsCached) {
                return this.value as MutableList<String>
            } else {
                listIsCached = true
                mutableListOf<String>().let {
                    jsonNode.forEach {
                        node ->
                        it.add(node.textValue())
                    }
                    return it
                }
            }
        }

    override fun get(key: String): IParam? {
        jsonNode.get(key)?.let {
            return JsonParam(it)
        }
        return null
    }

    override fun paramList(): List<IParam>? {
        val listToReturn = mutableListOf<IParam>()
        jsonNode.toMutableList().forEach {
            listToReturn.add(JsonParam(it))
        }
        return listToReturn
    }

}