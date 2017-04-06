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
    private val headers_map = HashMap<String, String>()

    constructor(buffered_reader: BufferedReader) {
        br = buffered_reader
        while(true) {
            var buffer = br.readLine()
            if(buffer == null ||buffer.isEmpty()) {
                break
            }
            val separator = buffer.indexOf(": ")
            if(separator > -1) {
                headers_map.put(buffer.substring(0, separator), buffer.substring(separator + 2))
            } else {
                protocol = buffer.substring(buffer.lastIndexOf(' ') + 1)
                method = buffer.substring(0, buffer.indexOf(' ')).toUpperCase()
                path = buffer.substring(buffer.indexOf(' ', buffer.indexOf(' ') + 1))
            }
        }
    }

    fun get_protocol() = protocol
    fun get_method() = method
    fun get_path() = path
    fun get_headers_map() = headers_map
}