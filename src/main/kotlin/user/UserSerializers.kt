package user

import at.wavywalk.simpler.orm.generated.user.UserToJsonSerializer

object UserSerializers {

    fun serializeForJwtToken(user: User): String {
        return UserToJsonSerializer(user)
            .serializeToString()
    }

}