package at.wavywalk.simpler.router

//acts as trait
interface INode {
    var name: String?

    var handler: RouteHandler?
    var hasHandler: Boolean
    fun assignHandler(value: RouteHandler) {
        hasHandler = true
        handler = value
    }

    var hasAnyTypeOfChildren: Boolean
    var hasPlainChildren: Boolean
    var hasWildChildren: Boolean
    var hasNamedChildren: Boolean

    val plainChildren: MutableMap<String, PlainNode>
    fun addPlainChild(partName: String, plainNode: PlainNode) {
        hasAnyTypeOfChildren = true
        hasPlainChildren = true
        plainChildren[partName] = plainNode
    }

    val namedChildren: NamedNodesContainer
    fun addNamedNode(namedNode: NamedNode) {
        hasAnyTypeOfChildren = true
        hasNamedChildren = true
        namedChildren.addNode(namedNode)
    }

    val wildChildren: WildNodesContainer
    fun addWildNode(wildNode: WildNode) {
        hasAnyTypeOfChildren = true
        hasWildChildren = true
        wildChildren.addNode(wildNode)
    }
}