package ru.mmishak.bicyclewalksspring.extentions

import javax.servlet.http.HttpServletRequest

operator fun HttpServletRequest.get(key: String): Any? {
    return this.getAttribute(key)
}