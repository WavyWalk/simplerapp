package at.wavywalk.simpler.orm.filegeneration

import at.wavywalk.simpler.orm.filegeneration.services.KotlinGeneratedDirPathAccessor
import java.io.File

object GeneratedFileFactory {

    fun createDefault(packageName: String, fileName: String): File {
        val completeFileName = "${fileName}.kt"
        val path = "${KotlinGeneratedDirPathAccessor.get()}/${packageName.split('.').joinToString("/")}"

        val directory = File(path)
        if (! directory.exists()){
            directory.mkdirs()
        }

        return File(
                path,
                completeFileName
        )
    }

}