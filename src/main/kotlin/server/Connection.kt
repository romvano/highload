package mint.ivan.highload.server

import mint.ivan.highload.server.Request
import mint.ivan.highload.server.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

/**
 * Created by ivan on 4/5/17.
 */
class Connection(s: Socket) : Runnable {
    var socket = s
    var inputStream = s.inputStream
    var outputStream = s.outputStream
    var buffer = BufferedReader(InputStreamReader(inputStream))

    override fun run() {
        try {
            var request = Request(buffer)
            Response(request.get_protocol(), request.get_method(), request.get_path(), outputStream)
                .send()
        } catch(e: IOException) {
            println(e.message)
        } finally {
            try {
                buffer.close()
                inputStream.close()
                outputStream.close()
                socket.close()
            } catch(e: IOException) {
                println(e.message)
            }
        }
    }
}