package at.wavywalk.simpler.router

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//TODO: rename to maybe SimplerRequestContext
class SimplerServletRequestContext(
        val request: HttpServletRequest,
        val response: HttpServletResponse,
        val routeParameters: Map<String, String>,
        val format: String?
)