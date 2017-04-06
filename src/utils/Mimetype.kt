package mint.ivan.highload.utils

import java.util.*

/**
 * Created by ivan on 4/4/17.
 */
enum class Mimetype(s: String) {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    SWF("application/x-shockwave-flash"),
    JSON("application/json"),
    TEXT("text/*");

    companion object {
        final var map = HashMap<String, Mimetype> ()
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

        fun get_for(t: String): Mimetype? {
            return map.get(t)
        }
    }
}