package at.wavywalk.simpler.router

class Tree {

    private val root = PlainNode()

    fun addRoute(path: String, handler: RouteHandler) {
        RouteAdder(path, handler, root).add()
    }

    fun findHandlerFor(uriPath: String): RouteSearcher {
        return  RouteSearcher(uriPath, root).find()
    }

}