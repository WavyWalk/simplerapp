package at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces

interface IToJsonSerializerObjectMapperAdapter {

    fun createArrayNode(): IArrayNode

    fun createObjectNode(): IObjectNode

}