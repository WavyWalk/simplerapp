package at.wavywalk.simpler.utils.cookies

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class WrappedCookies(
        val request: HttpServletRequest,
        val response: HttpServletResponse
) {

    private val wrappedCookies: MutableMap<String,Cookie> = mutableMapOf()

    init {
        request.cookies?.forEach {
            wrappedCookies[it.name] = it
        }
    }

    fun get(key: String): Cookie? {
        return wrappedCookies[key]
    }

    fun set(key: String, value: String?, maxAge: Int = -1, httpOnly: Boolean = true) {
        wrappedCookies[key]?.let {
            it.value = value
            it.maxAge = maxAge
            it.path = "/"
            it.domain = ""
            response.addCookie(it)
            return
        }
        Cookie(key, value).let {
            it.domain = ""
            it.path = "/"
            it.maxAge = maxAge
            it.isHttpOnly = httpOnly
            response.addCookie(it)
        }
    }

    fun addCookie(value: Cookie) {
        response.addCookie(value)
    }


    fun getString(key: String): String? {
        return wrappedCookies[key]?.value
    }

    fun getInt(key: String): Int? {
        wrappedCookies[key]?.let {
            if (it.value.isNotBlank()) {
                return Integer.parseInt(it.value)
            }
        }
        return null
    }

    fun delete(key: String) {
        wrappedCookies[key]?.let {
            it.maxAge = 0
            it.value = ""
            it.path = "/"
            response.addCookie(it)
        }
    }

}