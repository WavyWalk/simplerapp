package routes

import at.wavywalk.simpler.controllers.SimplerBaseController
import at.wavywalk.simpler.router.Router
import at.wavywalk.simpler.router.RoutesDrawer
import at.wavywalk.simpler.servletUtils.ToDefaultServletForwarder

import session.SessionController

object RoutesConfig: RoutesDrawer(Router) {

    override fun run() {

        val toDefaultServletForwarder = ToDefaultServletForwarder()

        get("/public/*") {
            toDefaultServletForwarder.forward(it)
        }

        get("/hello") {
            SimplerBaseController(it).renderPlain("hello")
        }

        namespace("/session") {
            get("/loginViaLink") {
                SessionController(it).loginViaLink()
            }
            get("/currentUser") {
                SessionController(it).currentUser()
            }
        }
        
    }
}