import com.fasterxml.jackson.databind.ObjectMapper

object App {

    var isInCliMode = false
    val objectMapper = ObjectMapper()

    lateinit var env: String

}