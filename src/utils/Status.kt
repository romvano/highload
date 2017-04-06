package mint.ivan.highload.utils

/**
 * Created by ivan on 4/4/17.
 */
enum class Status(s: String) {
    HTTP_PROTOCOL("HTTP/1.1"),
    OK("200 OK"),
    ERROR_403("403 Forbidden"),
    ERROR_404("404 Not Found"),
    ERROR_405("405 Method Not Allowed")
}