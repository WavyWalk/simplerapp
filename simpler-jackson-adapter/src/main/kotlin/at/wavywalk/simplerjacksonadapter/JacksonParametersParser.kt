package at.wavywalk.simplerjacksonadapter

import at.wavywalk.simpler.utils.requestparameters.IParam
import at.wavywalk.simpler.utils.requestparameters.IRequestParametersParser
import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletRequest


class JacksonParametersParser(
        val objectMapper: ObjectMapper
) : IRequestParametersParser {


    override fun parse(request: HttpServletRequest): IParam {
        objectMapper.readTree(request.inputStream).let {
            return JsonParam(it)
        }
    }

    override fun parse(request: HttpServletRequest, maxContentLength: Long?): IParam {
        throw Exception("unsupported")
    }


    override fun parse(toParse: String?): IParam? {
        objectMapper.readTree(toParse).let {
            return JsonParam(it)
        }
    }
}