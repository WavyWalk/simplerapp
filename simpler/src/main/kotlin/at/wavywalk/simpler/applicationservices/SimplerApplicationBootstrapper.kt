package at.wavywalk.simpler.applicationservices

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

abstract class SimplerApplicationBootstrapper : ServletContextListener {

    abstract fun runOnBootstrap()
    abstract fun runOnShutdown()

    override fun contextInitialized(servletContextEvent: ServletContextEvent) {
        runOnBootstrap()
    }

    override fun contextDestroyed(servletContextEvent: ServletContextEvent) {
        runOnShutdown()
    }
}