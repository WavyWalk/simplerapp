package at.wavywalk.simpler.router

class WildNodesContainer {
    var nodes: MutableList<WildNode> = mutableListOf()
    fun addNode(node: WildNode) {
        nodes.add(node)
    }
}