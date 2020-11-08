package at.wavywalk.simpler.orm.filegeneration

import at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel
import javax.lang.model.type.MirroredTypeException

object AggregateModelsBank {

    val models: HashMap<String, ModelDataModel> = hashMapOf()

    fun registerModel(modelDataModel: ModelDataModel) {
        val valueAtKey = models[modelDataModel.modelClass]
        if (valueAtKey != null) {
            throw Throwable("model ${modelDataModel.modelClass}: already registered")
        }
        models[modelDataModel.modelClass] = modelDataModel
    }

    fun getModel(block: ()->Unit): ModelDataModel {
        var associatedModelDataModel: ModelDataModel? = null
        val simpleName2: String
        try {
            block.invoke()
            throw Exception("block should throw MirroredTypeException")
        } catch (error: MirroredTypeException) {
            val typeMirror = error.typeMirror
            val simpleName = typeMirror.toString().split('.').last()
            simpleName2 = simpleName
            associatedModelDataModel = AggregateModelsBank.models[simpleName]
        }
        if (associatedModelDataModel == null) {
            throw Exception("Associated class not found ${simpleName2}")
        } else {
            return associatedModelDataModel
        }
    }

}