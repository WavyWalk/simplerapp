package authentication

import at.wavywalk.simpler.orm.generated.user.UserFromParamsSerializer
import at.wavywalk.simpler.orm.generated.user.UserRecord
import at.wavywalk.simpler.orm.generated.user.UserToJsonSerializer
import at.wavywalk.simpler.utils.cookies.WrappedCookies
import serialization.Serialization
import user.User

object CurrentUser {

    fun login(user: User, cookies: WrappedCookies) {
        val userString = UserToJsonSerializer(user).apply {
            only { it.id }
        }.serializeToString()
        JwtHandler.setUserPayload(cookies, userString)
    }

    fun get(cookies: WrappedCookies): User? {
        val user = getUserWithoutQuerying(cookies)
        user ?: return null
        return UserRecord.GET().whereIdEq(user.id).executeGetFirstOrNull()
    }

    fun getUserWithoutQuerying(cookies: WrappedCookies): User? {
        val userString = JwtHandler.getUserPayload(cookies)
        userString ?: return null
        val param = Serialization.paramsFromJson(userString)
        val user = UserFromParamsSerializer().serialize(param)
        if (user?.id == null) {
            JwtHandler.ensureDeleteToken(cookies)
        }
        return user
    }

}