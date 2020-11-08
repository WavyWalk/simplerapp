package at.wavywalk.simpler.servletUtils

import at.wavywalk.simpler.router.SimplerServletRequestContext

class ToDefaultServletForwarder {

    fun forward(context: SimplerServletRequestContext){
        context.request.session.servletContext.getNamedDispatcher("default").forward(context.request, context.response)
    }

}