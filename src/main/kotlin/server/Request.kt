package mint.ivan.highload.server

import java.io.BufferedReader
import java.net.URLDecoder
import java.util.*

/**
 * Created by ivan on 4/5/17.
 */
class Request {
    private val br: BufferedReader
    private var protocol = String()
    private var method = String()
    private var path = String()

    constructor(buffered_reader: BufferedReader) {
        br = buffered_reader
        while(true) {
            var buffer = br.readLine()
            if (buffer == null || buffer.isEmpty()) {
                break
            }
            val separator = buffer.indexOf(": ")
            if (separator == -1) {
                println("buffer: " + buffer)
                protocol = buffer.substring(buffer.lastIndexOf(' ') + 1)
                method = buffer.substring(0, buffer.indexOf(' ')).toUpperCase()
                path = buffer.substring(buffer.indexOf('/') + 1, buffer.indexOf(' ', buffer.indexOf(' ') + 1))
                path = URLDecoder.decode(path, "utf-8")
                println("the method is: " + method + ";")
                println("the path is:" + path)
            }
        }
    }

    fun get_protocol() = protocol
    fun get_method() = method
    fun get_path() = path
}