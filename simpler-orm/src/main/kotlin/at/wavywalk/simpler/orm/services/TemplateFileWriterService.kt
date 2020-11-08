package at.wavywalk.simpler.orm.services

import freemarker.template.Template
import at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel
import java.io.File

object TemplateFileWriterService {

    fun writeTemplate(
        template: Template,
        modelDataModel: ModelDataModel,
        file: File
    ){
        val writer = file.writer()
        template.process(modelDataModel, writer)
        writer.close()
    }

    fun writeTemplate(
            template: Template,
            dataModel: MutableMap<String,String>,
            file: File
    ) {
        val writer = file.writer()
        template.process(dataModel, writer)
        writer.close()
    }

}