package ru.mmishak.bicyclewalksspring.conrollers.web

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.mmishak.bicyclewalksspring.contants.STATUS_CODE_KEY
import ru.mmishak.bicyclewalksspring.extentions.get
import javax.servlet.http.HttpServletRequest

@Controller
class StatusController : ErrorController {

    @GetMapping("/status")
    fun status() = "status"

    @RequestMapping("/error")
    fun error(request: HttpServletRequest, model: Model): String {
        model["statusCode"] = request[STATUS_CODE_KEY] ?: "XXX"
        return "error"
    }

    override fun getErrorPath() = "/error"
}