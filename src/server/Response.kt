package mint.ivan.highload.server

import java.io.OutputStream
import mint.ivan.highload.utils.*
import sun.misc.IOUtils
import java.awt.List
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Created by ivan on 4/4/17.
 */
class Response(protocol: String, method: String, path: String, out: OutputStream) {
    private val protocol = protocol
    private val method = method
    private val path = path
    private var out = out

    private final val HEADER_DIVIDER = "\r\n"
    private final val GET_METHOD = "GET"
    private final val HEAD_METHOD = "HEAD"

    fun send() {
        if(!method.equals(GET_METHOD) && !method.equals(HEAD_METHOD)) {
            error(Status.ERROR_405)
            return
        }
        val path = ROOT_DIR.toString() + path.split("?")[0]
        if("../" in path) {
            error(Status.ERROR_404)
            return
        }
        var file = File(path)
        if(file.isDirectory) {
            file = File(path + INDEX_PAGE)
            if(file.exists()) {
                ok(file)
            } else {
                error(Status.ERROR_403)
            }
            return
        }
        if(file.exists()) {
            ok(file)
        } else {
            error(Status.ERROR_404)
        }
    }

    private fun make_header(code: Status, content_type: Mimetype = Mimetype.HTML, content_length: Long = 0L) : String {
        return String()
            .plus(protocol).plus(" ").plus(code.toString()).plus(HEADER_DIVIDER)
            .plus("Server: ivan_highload").plus(HEADER_DIVIDER)
            .plus("Connection: close").plus(HEADER_DIVIDER)
            .plus("Date: ").plus(Calendar.getInstance().time).plus(HEADER_DIVIDER)
            .plus("Content-Type: ").plus(content_type.toString()).plus(HEADER_DIVIDER)
            .plus("Content-Length ").plus(content_length).plus(HEADER_DIVIDER)
    }

    private fun error(code: Status) {
        out.flush()
        out.write(make_header(code).toByteArray())
    }

    private fun ok(file: File) {
        val i = file.absolutePath.lastIndexOf('.')
        val extension = if (i > 0) file.absolutePath.substring(i + 1).toLowerCase() else ""
        val mimetype = Mimetype.get_for(extension)
        // check if mimetype is valid
        if (mimetype == null) {
            error(Status.ERROR_403)
            return
        }
        out.flush()
        // write header
        out.write(make_header(Status.OK, mimetype, file.length()).toByteArray())
        out.write(HEADER_DIVIDER.toByteArray())
        // send file body if needed
        if(method == GET_METHOD) {
            FileInputStream(file).use {
                out.write(it.toString().toByteArray())
            }
        }
    }
}