package at.wavywalk.simpler.templating

import java.io.Writer

interface ITemplateProcessor {

    fun process(templateName: String, dataModel: Any, writer: Writer)

}