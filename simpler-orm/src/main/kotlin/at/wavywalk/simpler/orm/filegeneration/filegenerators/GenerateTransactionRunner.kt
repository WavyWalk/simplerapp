package at.wavywalk.simpler.orm.filegeneration.filegenerators

import freemarker.template.Template
import at.wavywalk.simpler.orm.filegeneration.GeneratedFileFactory
import at.wavywalk.simpler.orm.filegeneration.TemplateFactory
import at.wavywalk.simpler.orm.services.TemplateFileWriterService
import java.io.File

class GenerateTransactionRunner() {
    fun run(){
        val file: File = GeneratedFileFactory.createDefault(
                packageName = "orm.generated.utils",
                fileName = "TransactionRunner"
        )

        val template: Template = TemplateFactory.createTemplate("transactionRunner.ftl")

        TemplateFileWriterService.writeTemplate(template, mutableMapOf<String,String>(), file)
    }
}