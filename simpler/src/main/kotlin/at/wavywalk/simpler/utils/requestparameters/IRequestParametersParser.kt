package at.wavywalk.simpler.utils.requestparameters

import javax.servlet.http.HttpServletRequest

interface IRequestParametersParser {

    fun parse(request: HttpServletRequest): IParam

    fun parse(request: HttpServletRequest, maxContentLength: Long?): IParam

    fun parse(toParse: String?): IParam?

}