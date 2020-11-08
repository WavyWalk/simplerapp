package at.wavywalk.simpler.assetsmanagement

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty
import kotlin.reflect.KProperty


class PublicFolderConfig {

    @ConfigurationProperty
    var pathToPublicDir: String? = "/resources/public"

}

