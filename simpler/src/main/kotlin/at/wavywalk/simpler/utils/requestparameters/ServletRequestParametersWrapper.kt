package at.wavywalk.simpler.utils.requestparameters

import at.wavywalk.simpler.dependencymanagement.SimplerDependenciesProvider
import at.wavywalk.simpler.dependencymanagement.SimplerDependencyManager
import at.wavywalk.simpler.utils.requestparameters.multipartformdata.MultipartFormDataParametersParser
import org.apache.commons.fileupload.servlet.ServletFileUpload
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

class ServletRequestParametersWrapper(
    val jsonParametersParser: IRequestParametersParser,
    val multiPartFormDataParametersParser: IRequestParametersParser = MultipartFormDataParametersParser()
) {
    val applicationJson: String = "application/json"

    fun createTree(request: HttpServletRequest, maxContentLength: Long? = null): IParam {
        if (ServletFileUpload.isMultipartContent(request)) {
            return multiPartFormDataParametersParser.parse(request, maxContentLength)
        } else if (request.contentType?.startsWith("application/json") == true) {
            return jsonParametersParser.parse(request)
        } else {
            throw Exception()
        }
    }

}