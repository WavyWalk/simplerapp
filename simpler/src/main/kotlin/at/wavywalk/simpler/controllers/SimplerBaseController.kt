package at.wavywalk.simpler.controllers

import at.wavywalk.simpler.dependencymanagement.SimplerDependencyManager
import at.wavywalk.simpler.router.SimplerServletRequestContext
import at.wavywalk.simpler.utils.cookies.WrappedCookies
import at.wavywalk.simpler.utils.requestparameters.IParam
import java.io.PrintWriter
import java.io.StringWriter

open class SimplerBaseController(
       val context: SimplerServletRequestContext
) {

    fun requestParams(maxContentLength: Long? = null): IParam {
        return SimplerDependencyManager.provider.servletRequestParametersWrapper.createTree(context.request, maxContentLength)
    }

    fun routeParams(): Map<String, String> {
        return context.routeParameters
    }

    fun renderTemplate(
            templateName: String,
            dataModel: Any
    ){
        SimplerDependencyManager.provider.templateProcessor.process(
                templateName,
                dataModel,
                context.response.writer
        )
        context.response.writer.close()
    }

    fun renderPlain(stringToWrite: String) {
        context.response.writer.write(stringToWrite)
        context.response.writer.close()
    }

    fun cookies(): WrappedCookies {
        return WrappedCookies(context.request, context.response)
    }

    fun renderTemplateToString(
            templateName: String,
            dataModel: Any
    ): String {
        val stringWriter = StringWriter()
        val stringPrintWiriter = PrintWriter(stringWriter)
        SimplerDependencyManager.provider.templateProcessor.process(
                templateName,
                dataModel,
                stringPrintWiriter
        )
        stringPrintWiriter.let {
            it.flush()
            it.close()
        }
        return stringWriter.toString()
    }

    fun requestQueryStringParams(): Map<String,String> {
        return SimplerDependencyManager.provider.queryStringParametersWrapper.parse(context.request.queryString)
    }

    fun sendError(errorCode: Int) {
        context.response.sendError(errorCode)
    }

    fun head(statusCode: Int) {
        context.response.status = statusCode
        context.response.writer.close()
    }

    fun renderJson(body: String) {
        context.response.let {
            it.contentType = "application/json"
            it.characterEncoding = "UTF-8"
            it.writer.print(body)
            it.writer.close()
        }
    }

}






