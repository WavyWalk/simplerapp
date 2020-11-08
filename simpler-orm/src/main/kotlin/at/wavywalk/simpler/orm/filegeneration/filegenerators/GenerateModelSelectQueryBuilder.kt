package at.wavywalk.simpler.orm.filegeneration.filegenerators

import freemarker.template.Template
import at.wavywalk.simpler.orm.configs.templatingengine.TemplateFilePathsConfig
import at.wavywalk.simpler.orm.filegeneration.GeneratedFileFactory
import at.wavywalk.simpler.orm.filegeneration.TemplateFactory
import at.wavywalk.simpler.orm.services.TemplateFileWriterService
import at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel
import java.io.File

class GenerateModelSelectQueryBuilder(val modelDataModel: ModelDataModel) {
    fun run(){
        val file: File = GeneratedFileFactory.createDefault(
                packageName = modelDataModel.packagesBean.baseGenerated,
                fileName = "${modelDataModel.modelClass}SelectQueryBuilder"
        )

        val templateName: String = TemplateFilePathsConfig.modelSelectQueryBuilder
        val template: Template = TemplateFactory.createTemplate(templateName)

        TemplateFileWriterService.writeTemplate(template, modelDataModel, file)
    }
}