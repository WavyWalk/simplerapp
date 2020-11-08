//package at.wavywalk.simplerembeddedjetty
//
//import org.eclipse.jetty.server.Server
//import org.eclipse.jetty.server.ServerConnector
//import org.eclipse.jetty.servlet.DefaultServlet
//import org.eclipse.jetty.servlet.ServletContextHandler
//import org.eclipse.jetty.servlet.ServletHolder
//import org.eclipse.jetty.util.resource.Resource
//
//
//object AltDefaultServlet {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        System.setProperty("org.eclipse.jetty.LEVEL", "INFO")
//        val server = Server()
//        val connector = ServerConnector(server)
//        connector.port = 8080
//        server.addConnector(connector)
//        // The filesystem paths we will map
//        val homePath = System.getProperty("user.home")
//        val pwdPath = System.getProperty("user.dir")
//        // Setup the basic application "context" for this application at "/"
//// This is also known as the handler tree (in jetty speak)
//        val context = ServletContextHandler(ServletContextHandler.SESSIONS)
//        context.baseResource = Resource.newResource(pwdPath)
//        context.contextPath = "/"
//        server.handler = context
//
//
//        val holderHome = ServletHolder("static-home", DefaultServlet::class.java)
//        holderHome.setInitParameter("resourceBase", homePath)
//        holderHome.setInitParameter("dirAllowed", "true")
//        holderHome.setInitParameter("pathInfoOnly", "true")
//        context.addServlet(holderHome, "/home/*")
//        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
//// It is important that this is last.
//        val holderPwd = ServletHolder("default", DefaultServlet::class.java)
//        holderPwd.setInitParameter("dirAllowed", "true")
//        context.addServlet(holderPwd, "/")
//        try {
//            server.start()
//            server.dump(System.err)
//            server.join()
//        } catch (t: Throwable) {
//            t.printStackTrace(System.err)
//        }
//    }
//}