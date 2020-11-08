package at.wavywalk.simpler.orm.dependencymanagement.adapterinterfaces

interface IArrayNode {

    abstract fun add(node: IObjectNode)

    abstract fun add(value: String?)

    override abstract fun toString(): String

    abstract fun serializeToString(): String

}