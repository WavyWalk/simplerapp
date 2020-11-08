package utils.files

import App
import java.io.File




class ExecutableDirProvider {

    companion object {
        fun get(): String {
            val jarRoot = App::class.java.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()

            return File(jarRoot).getPath()
        }
    }

}