package session

import at.wavywalk.simpler.controllers.SimplerBaseController
import at.wavywalk.simpler.orm.generated.user.UserToJsonSerializer
import at.wavywalk.simpler.router.SimplerServletRequestContext
import authentication.CurrentUser
import user.UserRepository
import user.UserSerializers
import java.lang.IllegalStateException

class SessionController(ctx: SimplerServletRequestContext) : SimplerBaseController(ctx) {

    fun loginViaLink() {
        val linkToken = requestQueryStringParams().get("loginToken")
        linkToken ?: throw IllegalStateException()
        val user = UserRepository.findById(1)
        user ?: return head(403)
        CurrentUser.login(user, cookies())
        renderJson(UserSerializers.serializeForJwtToken(user))
    }

    fun currentUser() {
        val user = CurrentUser.get(cookies())
        user ?: return renderJson("{}")
        renderJson(UserToJsonSerializer(user).serializeToString())
    }

}