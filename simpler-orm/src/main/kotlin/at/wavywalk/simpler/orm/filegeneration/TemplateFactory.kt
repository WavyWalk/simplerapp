package at.wavywalk.simpler.orm.filegeneration

import at.wavywalk.simpler.orm.configs.templatingengine.TemplatingEngineConfig
import freemarker.template.Template

object TemplateFactory {

    fun createTemplate(templateName: String): Template {
        try {
            return TemplatingEngineConfig.templateEngineConfiguration.getTemplate(
                    templateName
            )
        } catch (error: Exception) {
            throw error
        }
    }

}