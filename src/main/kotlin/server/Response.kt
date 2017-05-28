package mint.ivan.highload.server

import java.io.OutputStream
import mint.ivan.highload.utils.*
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Created by ivan on 4/4/17.
 */
const val HTTP_PROTOCOL = "HTTP/1.1"
const val OK = "200 OK"
const val ERROR_403 = "403 Forbidden"
const val ERROR_404 = "404 Not Found"
const val ERROR_405 = "405 Method Not Allowed"

class Response(protocol: String, method: String, path: String, out: OutputStream) {
    private val protocol = protocol
    private val method = method
    private val path = path
    private var out = out

    private final val HEADER_DIVIDER = "\r\n"
    private final val GET_METHOD = "GET"
    private final val HEAD_METHOD = "HEAD"

    fun send() {
        println("in send")
        if(method != GET_METHOD && method != HEAD_METHOD) {
            println("405")
            error(ERROR_405)
            return
        }
        val path = ROOT_DIR + path.split("?")[0]
        if("../" in path) {
            println("..404")
            error(ERROR_404)
            return
        }
        println(path)
        var file = File(path)
        println("filepath: " + path)
        if(file.isDirectory) {
            println("file is dir")
            file = File(path + INDEX_PAGE)
            if(file.exists()) {
                println("send index in dir")
                ok(file)
            } else {
                println("no index in dir")
                error(ERROR_403)
            }
            return
        }
        if(file.exists()) {
            println("send file")
            ok(file)
        } else {
            println("404")
            error(ERROR_404)
        }
    }

    private fun make_header(code: String, content_type: String = HTML, content_length: Long = 0L) : String {
        val resp = String()
            .plus(protocol).plus(" ").plus(code).plus(HEADER_DIVIDER)
            .plus("Server: ivan_highload").plus(HEADER_DIVIDER)
            .plus("Connection: close").plus(HEADER_DIVIDER)
            .plus("Date: ").plus(Calendar.getInstance().time).plus(HEADER_DIVIDER)
            .plus("Content-Type: ").plus(content_type.toString()).plus(HEADER_DIVIDER)
            .plus("Content-Length: ").plus(content_length).plus(HEADER_DIVIDER)
        println(resp)
        return resp
    }

    private fun error(code: String) {
        out.flush()
        out.write(make_header(code).toByteArray())
    }

    private fun ok(file: File) {
        val i = file.absolutePath.lastIndexOf('.')
        val extension = if (i > 0) file.absolutePath.substring(i + 1).toLowerCase() else ""
        val mimetype = Mimetype.get_for(extension)
        // check if mimetype is valid
        if (mimetype == null) {
            println("403 mime")
            error(ERROR_403)
            return
        }
        println("begin sending")
        out.flush()
        // write header
        out.write(make_header(OK, mimetype, file.length()).toByteArray())
        println("header written")
        out.write(HEADER_DIVIDER.toByteArray())
        // send file body if needed
        if (method == GET_METHOD) {
            println("send file")
            FileInputStream(file).use {
                out.write(IOUtils.toByteArray(it))
            }
        }
    }
}