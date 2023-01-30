package cn.repigeons.njnu.classroom.commons.component

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"], filterName = "CharacterEncodingFilter")
class CharacterEncodingFilter : Filter {
    override fun init(filterConfig: FilterConfig) {}
    override fun destroy() {}
    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        request.characterEncoding = "UTF-8"
        response.characterEncoding = "UTF-8"
        filterChain.doFilter(request, response)
    }
}