package at.wavywalk.simpler.freemarkeradapter

import at.wavywalk.simpler.templating.ITemplateProcessor
import freemarker.template.Configuration
import java.io.Writer

class FreemarkerTemplateProcessor(val configuration: Configuration) : ITemplateProcessor {

    override fun process(templateName: String, dataModel: Any, writer: Writer) {
        val template = configuration.getTemplate(templateName)
        template.process(dataModel, writer)
    }

}