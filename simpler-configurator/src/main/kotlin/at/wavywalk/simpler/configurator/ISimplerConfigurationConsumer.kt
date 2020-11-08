package at.wavywalk.simpler.configurator

interface ISimplerConfigurationConsumer {

    abstract fun consume(objectToConfigure: Any, pathToConfigurationFile: String,
                         vararg keyPathToConfigurationPropertiesInFile: String): Unit

}