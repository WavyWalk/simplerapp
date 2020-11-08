package at.wavywalk.simpler.servlets

import at.wavywalk.simpler.router.Router
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.annotation.MultipartConfig

@WebServlet(name = "frontServlet", urlPatterns = arrayOf("/"))
@MultipartConfig
class FrontServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doGet(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doPost(req, resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doDelete(req, resp)
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doPut(req, resp)
    }

}