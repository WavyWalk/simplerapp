package conf

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty

class DbConf {

    companion object {

        @ConfigurationProperty
        lateinit var userName: String

        @ConfigurationProperty
        lateinit var password: String

        @ConfigurationProperty
        lateinit var jdbcUrl: String

        @ConfigurationProperty
        lateinit var driverClassName: String

    }

}