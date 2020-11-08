package at.wavywalk.simpler.orm.services

class ModelInvalidError(
        override val message: String? = "invalid"
) : Throwable(message) {

}