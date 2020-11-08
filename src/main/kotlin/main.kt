import at.wavywalk.simplerembeddedjetty.SimplerEmbeddedJettyStarter
import conf.PublicDirConf

fun main() {
    ApplicationBootstrapper().runOnBootstrap()
    val server = SimplerEmbeddedJettyStarter()
    server.startServer(8085, ApplicationBootstrapper(), PublicDirConf.publicDir)
}


