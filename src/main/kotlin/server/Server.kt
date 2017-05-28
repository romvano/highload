package mint.ivan.highload.server

import mint.ivan.highload.utils.ROOT_DIR
import java.net.ServerSocket
import java.util.concurrent.ForkJoinPool

/**
 * Created by ivan on 4/5/17.
 */

class Server constructor(port: Int, dir: String, cpu: Int = Runtime.getRuntime().availableProcessors(), queue: Int = 120)  {
    private val port = port
    private val dir = dir
    private val cpu = cpu
    private val queue = queue
    private var isRunning = true
    private val serverSocket = ServerSocket(port, queue)
    private val pool = ForkJoinPool(cpu * 2 + 1)

    init {
        ROOT_DIR = dir
    }

    fun details() {
        print("Running with params\n"
            .plus("port: $port\n")
            .plus("root: $dir\n")
            .plus("cpus: $cpu\n")
            .plus("queues: $queue\n"))
    }

    fun start() {
        details()
        while(isRunning) {
            var socket = serverSocket.accept()
            var connection = Connection(socket)
            pool.execute(connection)
        }
    }

    fun stop() {
        isRunning = false
    }
}