package at.wavywalk.simpler.orm.filegeneration.filegenerators

import at.wavywalk.simpler.orm.templatedatamodels.model.ModelDataModel

object GenerateModelFiles {

    private fun generate(modelDataModel: ModelDataModel) {

        GenerateModelUtils(modelDataModel).run()

        GenerateModelSelectQueryBuilder(modelDataModel).run()

        GenerateModelInsertqueryBuilder(modelDataModel).run()

        GenerateModelUpdateQueryBuilder(modelDataModel).run()

        GenerateModelDeleteQueryBuilder(modelDataModel).run()

        GenerateModelToJsonSerializer(modelDataModel).run()

        GenerateModelPropertiesChangeTracker(modelDataModel).run()

        GenerateModelRecord(modelDataModel).run()

        GenerateModelAssociationsPreloader(modelDataModel).run()

        GenerateModelJoinBuilder(modelDataModel).run()

        GenerateModelFieldsAccessor(modelDataModel).run()

        GenerateModelDefaultResultSetParser(modelDataModel).run()

        GenerateModelDefaultCreator(modelDataModel).run()

        GenerateModelDefaultFinder(modelDataModel).run()

        GenerateModelDefaultUpdater(modelDataModel).run()

        GenerateModelDefaultDeleter(modelDataModel).run()

        GenerateModelAssociationsManager(modelDataModel).run()

        GenerateModelValidationManager(modelDataModel).run()

        GenerateModelValidatorTrait(modelDataModel).run()

        GenerateStringifiedNameForProperty(modelDataModel).run()

        GenerateModelFromParamsSerializer(modelDataModel).run()

        GenerateModelMerger(modelDataModel).run()

        if (modelDataModel.shouldGenerateRequestParamsWrapper) {
            //deprecated shall be cleaned up
            GenerateRequestParamsWrapper(modelDataModel).run()
        }

    }

    fun generate(modelDataModels: MutableCollection<ModelDataModel>) {
        modelDataModels.forEach {
            generate(it)
        }
    }


}