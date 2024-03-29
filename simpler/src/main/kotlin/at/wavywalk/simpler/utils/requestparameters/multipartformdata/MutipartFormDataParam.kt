package at.wavywalk.simpler.utils.requestparameters.multipartformdata

import at.wavywalk.simpler.utils.requestparameters.IParam
import at.wavywalk.simpler.utils.requestparameters.ParamInParseModeTrait
import at.wavywalk.simpler.utils.requestparameters.ParamTypesEnum
import org.apache.commons.fileupload.FileItem
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class MultipartFormDataParam : IParam, ParamInParseModeTrait {

    override var value: Any? = null
    override var valueType: ParamTypesEnum = ParamTypesEnum.UNSET
    var listIsCached = false

    override val string: String?
        get() {
            return this.value as String?
        }

    override val boolean: Boolean?
        get() {
            val stringValue = this.value as String?
            if (stringValue != null) {
                return (stringValue == "true")
            } else {
                return null
            }
        }

    override val timestamp: Timestamp?
        get() {
            val textValue = this.value as String?
            textValue ?: return null
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
                val parsedDate = dateFormat.parse(textValue)
                val timestamp = java.sql.Timestamp(parsedDate.time)
                return timestamp
            } catch (error: Exception) {
                return null
            }
        }

    override val intList: MutableList<Int>?
        get() {
            if (this.valueType != ParamTypesEnum.ANY_LIST) {
                throw IllegalStateException("param does not contain list as value")
            }
            if (listIsCached) {
                return this.value as MutableList<Int>
            } else {
                val intList = mutableListOf<Int>()
                (this.value as List<String>).forEach {
                    intList.add(Integer.parseInt(it))
                }
                this.listIsCached = true
                this.value = intList
                return intList
            }
        }

    override val stringList: MutableList<String>?
        get() {
            if (this.valueType != ParamTypesEnum.ANY_LIST) {
                throw IllegalStateException("param does not contain list as value")
            }
            return this.value as MutableList<String>
        }


    override fun getOrNewUnsetParam(key: String): ParamInParseModeTrait {
        val map = getOrInitializeParamMap()
        map[key]?.let {
            return it
        }
        MultipartFormDataParam().let {
            map[key] = it
            return it
        }
    }

    override fun getNodeFromNodeListOrNewAt(index: Int): IParam {
        return this.getMultiPartFormDataParamNodeFromNodeListOrAddNew(index)
    }

    fun getOrNewUnsetMultipartFormDataParam(key: String): MultipartFormDataParam {
        return this.getOrNewUnsetParam(key) as MultipartFormDataParam
    }

    fun addToAnyListFileItemUnboxingItIfNeccessary(fileItem: FileItem) {
        if (fileItem.isFormField) {
            addToAnyListOrInitializeAndAdd(fileItem.getString("UTF-8"))
        } else {
            addToAnyListOrInitializeAndAdd(fileItem)
        }
    }

    fun getMultiPartFormDataParamNodeFromNodeListOrAddNew(index: Int): MultipartFormDataParam {
        this.nodeListOrInitialize<MultipartFormDataParam>().let {
            it.elementAtOrNull(index)?.let {
                return it
            }
            MultipartFormDataParam.Companion.createNode().let {
                param ->
                it.add(param)
                return param
            }
        }
    }

    fun getOrNewUnsetMultiPartFormDataParam(key: String): MultipartFormDataParam {
        getOrInitializeParamMap().let {
            it[key]?.let {
                return it as MultipartFormDataParam
            }

            MultipartFormDataParam().let {
                param ->
                it[key] = param
                return param
            }
        }
    }

    override val long: Long?
        get() {
            this.value?.let {
                return (it as String).toLongOrNull()
            } ?: return null
        }

    override val int: Int?
        get() {
            this.value?.let {
                return (it as String).toIntOrNull()
            } ?: return null
        }

    override val localDateTime: LocalDateTime?
        get() {
            this.value?.let {
                return LocalDateTime.parse(it as String)
            } ?: return null
        }

    override val double: Double?
        get() {
            this.value?.let {
                return (it as String).toDouble()
            }
            return null
        }

    fun setBoxedValue(fileItem: FileItem) {
        if (fileItem.isFormField) {
            this.valueType = ParamTypesEnum.STRING
            this.value = fileItem.getString("UTF-8")
        } else {
            this.valueType = ParamTypesEnum.BOXED_VALUE
            this.value = fileItem
        }
    }
    companion object {
        fun createNode(): MultipartFormDataParam {
            MultipartFormDataParam().let {
                it.getOrInitializeParamMap()
                return it
            }
        }
        fun createUnset(): MultipartFormDataParam {
            return MultipartFormDataParam()
        }

    }

}
