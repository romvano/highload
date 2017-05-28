package mint.ivan.highload.utils

import java.util.*

/**
 * Created by ivan on 4/4/17.
 */

const val HTML = "text/html"
const val CSS = "text/css"
const val JS = "text/javascript"
const val JPEG = "image/jpeg"
const val PNG = "image/png"
const val GIF = "image/gif"
const val SWF = "application/x-shockwave-flash"
const val JSON = "application/json"
const val TEXT = "text/*"

class Mimetype {
    companion object {
        final var map = HashMap<String, String?> ()
            .plus(Pair("html", HTML))
            .plus(Pair("htm", HTML))
            .plus(Pair("css", CSS))
            .plus(Pair("js", JS))
            .plus(Pair("jpg", JPEG))
            .plus(Pair("jpeg", JPEG))
            .plus(Pair("png", PNG))
            .plus(Pair("gif", GIF))
            .plus(Pair("swf", SWF))
            .plus(Pair("json", JSON))
            .plus(Pair("txt", TEXT))

        fun get_for(t: String?): String? {
            return map.get(t)
        }
    }
}