package at.wavywalk.simpler.orm.templatedatamodels.model

object AddSerializableFieldsToModelDataModels {

    fun add(modelDataModels: MutableCollection<ModelDataModel>) {
        modelDataModels.forEach {model ->
            add(model)
        }
    }

    fun add(modelDataModel: ModelDataModel) {
        val serializableOnlyFields = SerializableOnlyFieldBeanFactory.create(modelDataModel.typeElement)
        modelDataModel.serializeOnlyFields = serializableOnlyFields
    }



}