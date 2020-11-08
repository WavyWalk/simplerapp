package at.wavywalk.simpler.utils.requestparameters

import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItem
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.nio.file.Files
import java.sql.Timestamp
import java.time.LocalDateTime

interface IParam {

    fun get(key: String): IParam? {
        if (this.valueType != ParamTypesEnum.NODE_ROOT) {
            throw IllegalStateException()
        }
        return (this.value as MutableMap<String, IParam>)[key]
    }

    val string: String?

    val timestamp: Timestamp?

    val boolean: Boolean?

    val intList: MutableList<Int>?

    val stringList: MutableList<String>?

    val long: Long?

    val int: Int?

    val localDateTime: LocalDateTime?

    val double: Double?


    fun fileItem(): FileItem? {
        return this.value as FileItem?
    }


    fun tempFile(): File? {
        val fileItem = fileItem()
        if (fileItem == null) {
            return null
        }
        if (fileItem.isInMemory) {
            try {
                val tempFile = Files.createTempFile("${System.currentTimeMillis()}-", ".tmp").toFile()
                fileItem.write(tempFile)
                return tempFile
            } catch (e: Exception) {
                return null
            }
        } else {
            val diskFileItem = fileItem as DiskFileItem
            return diskFileItem.storeLocation
        }
    }

    fun fileStream(): InputStream? {
        return this.value as InputStream?
    }

    fun paramList(): List<IParam>? {
        return this.value as MutableList<IParam>
    }

    fun nodeList(): List<IParam>? {
        return this.value as MutableList<IParam>
    }

    fun anyList(): List<Any> {
        return this.value as MutableList<Any>
    }

    fun boxedValueList(): MutableList<IParam>{
        if (this.valueType != ParamTypesEnum.BOXED_VALUE_LIST) {
            throw IllegalStateException()
        }
        return this.value as MutableList<IParam>
    }

    var value: Any?
    var valueType: ParamTypesEnum

}



