package authentication

import at.wavywalk.simpler.router.SimplerServletRequestContext
import at.wavywalk.simpler.utils.cookies.WrappedCookies
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.Payload
import java.lang.Exception
import java.time.LocalDateTime
import java.util.*


object JwtHandler {

    val verifier = JWT.require(JwtConf.algorithm)
        .withIssuer(JwtConf.issuer)
        .build()

    fun getDecodedToken(cookies: WrappedCookies): DecodedJWT? {
        val stringToken = cookies.get("jwt")?.value
        val jwt: DecodedJWT = verifier.verify(stringToken)
        return JWT.decode(stringToken)
    }

    fun getUserPayload(cookies: WrappedCookies): String? {
        try {
            val token = getDecodedToken(cookies)
            token ?: return null
            return token.getClaim("user")?.asString()
        } catch (e: Exception) {
            ensureDeleteToken(cookies)
            return null
        }
    }

    fun ensureDeleteToken(cookies: WrappedCookies) {
        cookies.delete("jwt")
    }

    fun setUserPayload(wrappedCookies: WrappedCookies, userPayload: String) {
        val token = JWT.create()
            .withIssuer(JwtConf.issuer)
            .withClaim("user", userPayload)
            .withIssuedAt(Date())
            .sign(JwtConf.algorithm);
        wrappedCookies.set("jwt", token, maxAge = JwtConf.tokenAge)
    }

}