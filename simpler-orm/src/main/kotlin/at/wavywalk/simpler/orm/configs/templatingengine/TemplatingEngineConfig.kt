package at.wavywalk.simpler.orm.configs.templatingengine

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler


object TemplatingEngineConfig : Runnable {

    var hasRun : Boolean = false
    lateinit var templateEngineConfiguration: Configuration

    fun runIfNotRunAlready() {
        if (!hasRun) {
            run()
        }
    }

    @Throws override fun run() {
        throwDuplicateRunErrorIf(hasRun)
        markSelfAsRun()

        templateEngineConfiguration = initializeConfigurationObject()
        setConfigProperties()
    }

    private fun setConfigProperties() {
//        val resource = this.javaClass.getResource("/templates/transactionRunner.ftl")
//
//        InputStreamReader(resource.openStream()).use {
//            throw Exception(it.readText())
//        }

        templateEngineConfiguration.apply {
            setClassForTemplateLoading(
                    this.javaClass, "/templates"
            )
            setDefaultEncoding("UTF-8")
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
            setLogTemplateExceptions(false)
        }
    }

    private fun initializeConfigurationObject(): Configuration {
        return Configuration(Configuration.VERSION_2_3_26)
    }

    private fun markSelfAsRun() {
        hasRun = true
    }

    private fun throwDuplicateRunErrorIf(hasRun: Boolean) {
        if (hasRun) {
            throw Throwable("TemplatingEngineConfig.run() called multiple times")
        }
    }



}