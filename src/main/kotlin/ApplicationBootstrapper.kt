import at.wavywalk.simpler.applicationservices.SimplerApplicationBootstrapper
import at.wavywalk.simpler.assetsmanagement.AssetsPathProvider
import at.wavywalk.simpler.assetsmanagement.PublicFolderConfig
import at.wavywalk.simpler.configurator.YamlConfigurationConsumer
import at.wavywalk.simpler.dependencymanagement.SimplerDependenciesProvider
import at.wavywalk.simpler.dependencymanagement.SimplerDependencyManager
import at.wavywalk.simpler.freemarkeradapter.FreemarkerTemplateProcessor
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesManager
import at.wavywalk.simpler.orm.dependencymanagement.SimplerOrmDependenciesProvider
import at.wavywalk.simpler.orm.jacksonadapter.JacksonObjectMapperAdapter
import at.wavywalk.simpler.utils.requestparameters.ServletRequestParametersWrapper
import at.wavywalk.simplerjacksonadapter.JacksonParametersParser
import authentication.JwtConf
import com.fasterxml.jackson.databind.ObjectMapper
import conf.*
import routes.RoutesConfig
import db.DbProvider
import freemarker.template.Configuration
import utils.files.ExecutableDirProvider
import javax.servlet.annotation.WebListener


@WebListener
class ApplicationBootstrapper : SimplerApplicationBootstrapper() {

    override fun runOnBootstrap() {
        App.env = System.getenv("APP_ENV") ?: "dev"
        readConfigs()
        setSimplerDependencies()
        setSimplerOrmDependencies()
        RoutesConfig.run()
    }

    override fun runOnShutdown() {

    }

    private fun readConfigs() {
        YamlConfigurationConsumer.consume(
            PublicDirConf,
            "conf/publicdir.yaml",
            App.env
        )
        PublicDirConf.publicDir = ExecutableDirProvider.get() + "/" + PublicDirConf.publicDir
        println("PUBLIC DIR ${PublicDirConf.publicDir}")

        YamlConfigurationConsumer.consume(
            DbConf,
            "conf/db.yaml",
            App.env
        )
        DbProvider.initialize(DbConf)

        YamlConfigurationConsumer.consume(
            JwtConf,
            "conf/jwt.yaml",
            App.env
        )

    }

    private fun setSimplerDependencies() {
        val publicFolderConfig = getPublicFolderConfig()
        val templateConfiguration = getTemplateConfig()
        val jsonParametersParser = JacksonParametersParser(ObjectMapper())
        val servletRequestParametersWrapper = ServletRequestParametersWrapper(jsonParametersParser)
        val assetsProvider = AssetsPathProvider(publicFolderConfig)
        val templateProcessor = FreemarkerTemplateProcessor(templateConfiguration)

        SimplerDependencyManager.provider = SimplerDependenciesProvider(
            servletRequestParametersWrapper,
            templateProcessor,
            assetsProvider
        )
    }

    private fun getTemplateConfig(): Configuration {
        return Configuration(Configuration.VERSION_2_3_0).also {
            it.setClassForTemplateLoading(
                this::class.java, "/templates")
        }
    }

    private fun getPublicFolderConfig(): PublicFolderConfig {
        return PublicFolderConfig().also {
            it.pathToPublicDir = "./"
        }
    }

    private fun setSimplerOrmDependencies() {
        SimplerOrmDependenciesManager.provider = SimplerOrmDependenciesProvider(
            DbProvider.dslContext,
            JacksonObjectMapperAdapter()
        )
    }
}