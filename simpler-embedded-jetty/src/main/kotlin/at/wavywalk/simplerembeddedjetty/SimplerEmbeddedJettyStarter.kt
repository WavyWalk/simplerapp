package at.wavywalk.simplerembeddedjetty

import at.wavywalk.simpler.applicationservices.SimplerApplicationBootstrapper
import at.wavywalk.simpler.servlets.FrontServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.Slf4jRequestLog
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.session.SessionHandler
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder


class SimplerEmbeddedJettyStarter {

    fun startServer(port: Int = 8080,
                    contextListener: SimplerApplicationBootstrapper,
                    publicDir: String
    ) {
        val server = Server(port)

        val defaultServlet = DefaultServlet()
        val defaultServletHolder = ServletHolder("default", defaultServlet)
        defaultServletHolder.setInitParameter("resourceBase", "./src/main/webapp")
        defaultServletHolder.setInitParameter("dirAllowed", "false")

        val servletContextHandler = ServletContextHandler().also {
            it.contextPath = "/"
            it.addEventListener(contextListener)
            it.addServlet(FrontServlet::class.java, "/*")
            it.addServlet(defaultServletHolder, "/-")
            it.addServlet(getFileServingContext(publicDir), "/public/*")
            it.sessionHandler = SessionHandler()

        }

        val handlers = HandlerList().also {
            it.addHandler(servletContextHandler)
        }

        server.requestLog = Slf4jRequestLog()

        server.handler = handlers
        server.start()
        server.join()
    }


    fun getFileServingContext(publicDir: String): ServletHolder {
        val holderHome = ServletHolder("static-home", DefaultServlet::class.java)
        holderHome.setInitParameter("resourceBase", publicDir)
        holderHome.setInitParameter("dirAllowed", "false")
        holderHome.setInitParameter("pathInfoOnly", "true")
        return holderHome
    }


}