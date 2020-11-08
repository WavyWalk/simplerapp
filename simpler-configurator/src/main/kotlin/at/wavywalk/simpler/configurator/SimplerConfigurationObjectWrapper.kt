package at.wavywalk.simpler.configurator

class SimplerConfigurationObjectWrapper(
    val pathToConfigurationFile: String,
    val keyPathToConfigurationPropertiesInFile: Array<out String>,
    val configurationObject: Any
) {

}