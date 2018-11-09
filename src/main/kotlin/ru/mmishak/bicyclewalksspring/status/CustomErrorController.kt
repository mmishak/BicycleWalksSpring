package ru.mmishak.bicyclewalksspring.status

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest


@Controller
class CustomErrorController : ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, model: Model): String {
        model["statusCode"] = request["javax.servlet.error.status_code"] ?: "XXX"
        return "error"
    }

    override fun getErrorPath() = "/error"

    private operator fun HttpServletRequest.get(key: String): Any? {
        return this.getAttribute(key)
    }
}
