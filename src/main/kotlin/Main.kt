/**
 * Created by ivan on 4/3/17.
 */

import org.apache.commons.cli.Options
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.ParseException
import java.io.Console
import java.io.File
import java.net.BindException

import mint.ivan.highload.utils.Mimetype
import mint.ivan.highload.server.Server

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val options = Options()
                .addOption("h", "help", false, "Show help.")
                .addOption("c", "cpu", true, "Set cpu number")
                .addOption("r", "root", true, "Set root dir")
                .addOption("p", "port", true, "Set port")
                .addOption("q", "queue", true, "Socket queue")
        var parser = DefaultParser().parse(options, args)
        if (parser.hasOption("h")) {
            show_help(options)
        }
        try {
            val port = Integer.valueOf(parser.getOptionValue("p"))
            val cpu = Integer.valueOf(parser.getOptionValue("c"))
            val root = parser.getOptionValue("r").replace("~", System.getProperty("user.home"))
            val file = File(root)
            val queue = Integer.valueOf(parser.getOptionValue("q"))

            val available_cpus = Runtime.getRuntime().availableProcessors()
            if (cpu > available_cpus) {
                println("Too many CPUs. You have only $available_cpus")
                System.exit(1)
            }
            if (!file.exists() || !file.canRead()) {
                println("Can't read file $file")
                System.exit(2)
            }

            val server = Server(port, root, cpu, queue)
            server.start()
        } catch(e: ParseException) {
            show_help(options)
        } catch(e: NumberFormatException) {
            show_help(options)
        } catch(e: BindException) {
            println(e.message)
        }
    }

    fun show_help(options: Options) {
        HelpFormatter().printHelp("Help", options)
        System.exit(0)
    }

}
