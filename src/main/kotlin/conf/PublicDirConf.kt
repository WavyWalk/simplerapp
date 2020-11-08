package conf

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty


class PublicDirConf {

    companion object {
        @ConfigurationProperty
        lateinit var publicDir: String
    }

}