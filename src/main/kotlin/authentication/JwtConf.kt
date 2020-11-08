package authentication

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty
import com.auth0.jwt.algorithms.Algorithm

object JwtConf {

    @ConfigurationProperty
    lateinit var secret: String

    var issuer: String = "hans"

    val algorithm: Algorithm by lazy { Algorithm.HMAC256(secret) }

    val tokenAge = 365 * 24 * 60 * 60

}