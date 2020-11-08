package at.wavywalk.simpler.orm.filegeneration.services

import javax.annotation.processing.ProcessingEnvironment

object KotlinGeneratedDirPathAccessor {

    private const val KAPT_KOTLIN_GENERATED__PROCESSING_ENV_OPTION_NAME = "kapt.kotlin.generated"

    private lateinit var kotlinGeneratedDirPath: String


    fun get(): String {
        return kotlinGeneratedDirPath
    }

    private fun throwInvalidDir() {
        throw Throwable("kotlin generated dir couldn't be fetched")
    }

    fun setByExtractingFrom(processingEnv: ProcessingEnvironment) {
        val dir = processingEnv.options[KAPT_KOTLIN_GENERATED__PROCESSING_ENV_OPTION_NAME]

        if (dir == null) {
            throwInvalidDir()
        } else {
            kotlinGeneratedDirPath = dir
        }
    }


}