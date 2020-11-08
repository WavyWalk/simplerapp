package at.wavywalk.simpler.orm.templatedatamodels.model

object FieldTypeImportPathSetter {

    fun getPathToType(typeName: String): MutableList<String> {
        return when (typeName) {
            "Timestamp?", "Timestamp" -> mutableListOf<String>(
                    "java.sql.Timestamp",
                    "java.time.Instant"
            )
            "LocalDateTime?", "LocalDateTime" -> mutableListOf<String>(
                "java.time.LocalDateTime"
            )
            else -> mutableListOf()

        }
    }

}