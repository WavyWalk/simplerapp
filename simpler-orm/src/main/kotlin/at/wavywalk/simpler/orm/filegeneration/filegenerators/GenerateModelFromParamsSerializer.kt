package at.wavywalk.simpler.orm.filegeneration.filegenerators

import at.wavywalk.simpler.orm.configs.templatingengine.TemplateFilePathsConfig
import at.wavywalk.simpler.orm.filegeneration.GeneratedFileFactory
import at.wavywalk.simpler.orm.filegeneration.TemplateFactory
import at.wavywalk.simpler.orm.services.TemplateFileWriterService
import at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel
import freemarker.template.Template
import java.io.File

class GenerateModelFromParamsSerializer(val modelDataModel: ModelDataModel) {

    fun run(){
        val file: File = GeneratedFileFactory.createDefault(
            packageName = modelDataModel.packagesBean.baseGenerated,
            fileName = "${modelDataModel.modelClass}FromParamsSerializer"
        )

        val templateName: String = TemplateFilePathsConfig.fromParamsSerializerTemplate
        val template: Template = TemplateFactory.createTemplate(templateName)

        TemplateFileWriterService.writeTemplate(template, modelDataModel, file)
    }

}