package at.wavywalk.simpler.configurator

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty
import org.yaml.snakeyaml.Yaml
import java.io.InputStream
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

object YamlConfigurationConsumer : ISimplerConfigurationConsumer {

    override fun consume(objectToConfigure: Any, pathToConfigurationFile: String,
                         vararg keyPathToConfigurationPropertiesInFile: String
//                         keyPathToConfigurationPropertiesInFile: Array<String>
    ) {
        val configurationObjectWrapper = SimplerConfigurationObjectWrapper(
            pathToConfigurationFile = pathToConfigurationFile,
            keyPathToConfigurationPropertiesInFile = keyPathToConfigurationPropertiesInFile,
            configurationObject = objectToConfigure
        )
        ensureThatConfigurationFileExists(
            configurationObjectWrapper
        )
        val propertiesMap =
            constructPropertiesMapFromConfigurationFileEnsuringThatConfigurationExistsForKeysPath(
                configurationObjectWrapper
            )
        populateConfigurationObject(
            configurationObjectWrapper,
            propertiesMap
        )
    }

    private fun ensureThatConfigurationFileExists(configurationObjectWrapper: SimplerConfigurationObjectWrapper) {
        this.javaClass.classLoader.getResource(configurationObjectWrapper.pathToConfigurationFile) 
            ?: throw Exception("no configuration file at ${configurationObjectWrapper.pathToConfigurationFile} " +
                    "for ${configurationObjectWrapper::class.qualifiedName}")
    }

    private fun constructPropertiesMapFromConfigurationFileEnsuringThatConfigurationExistsForKeysPath(
        configurationObjectWrapper: SimplerConfigurationObjectWrapper
    ): MutableMap<String, Any?> {
        var valueToReturn: MutableMap<String, Any?>? = null

        useResourceInputStream(configurationObjectWrapper.pathToConfigurationFile) {
            val configurationFileProperties = Yaml().load(it) as MutableMap<String, MutableMap<String, Any?>>
            valueToReturn = digToConfigurationMap(
                configurationObjectWrapper.keyPathToConfigurationPropertiesInFile, configurationFileProperties
            )
        }

        valueToReturn?.let {
            return it
        } ?: throw Exception("no properties for keys path: " +
                "${configurationObjectWrapper.keyPathToConfigurationPropertiesInFile} " +
                "defined in configuration file under: ${configurationObjectWrapper.pathToConfigurationFile}"
        )
    }

    private fun digToConfigurationMap(
        keys: Array<out String>,
        map: MutableMap<String,
        MutableMap<String, Any?>>?
    ):MutableMap<String, Any?>? {
        var mapAtCurrentKey = map
        for (i in 0 until keys.size - 1) {
            mapAtCurrentKey ?: return null
            val key = keys[i]
            println(key)
            mapAtCurrentKey = mapAtCurrentKey.getOrDefault(key, null) as MutableMap<String,
                    MutableMap<String, Any?>>?
        }
        return mapAtCurrentKey?.getOrDefault(keys.last(), null) as MutableMap<String, Any?>?
    }

    private fun  useResourceInputStream(
        pathToConfigurationFile: String,
        block: (InputStream)->Unit
    ) {
        val configFileStream = this.javaClass.classLoader.getResourceAsStream(pathToConfigurationFile)!!
        try {
            configFileStream.use(block)
        } catch (error: Exception) {
            throw error
        }
    }

    private fun setProperty(configurationObjectWrapper: SimplerConfigurationObjectWrapper,
                            configurationProperty: KMutableProperty<*>,
                            propertiesMap: MutableMap<String, Any?>,
                            configurationPropertyAnnotation: ConfigurationProperty
    ) {
        validateConfigurationPropertyWhenSetting(
            configurationObjectWrapper, configurationProperty,
            propertiesMap, configurationPropertyAnnotation
        )
        println("==========================================")
        println(configurationProperty.name)
        println(propertiesMap)
        println(propertiesMap[configurationProperty.name])

        configurationProperty.setter.call(configurationObjectWrapper.configurationObject, propertiesMap[configurationProperty.name])
    }

    private fun populateConfigurationObject(configurationObjectWrapper: SimplerConfigurationObjectWrapper,
                                            propertiesMap: MutableMap<String, Any?>
    ) {
        configurationObjectWrapper.configurationObject::class.declaredMemberProperties.forEach {
            val configurationPropertyAnnotation = it.findAnnotation<ConfigurationProperty>()
            if (configurationPropertyAnnotation != null) {
                setProperty(
                    configurationObjectWrapper, it as KMutableProperty<*>,
                    propertiesMap, configurationPropertyAnnotation
                )
            }
        }
    }

    private fun validateConfigurationPropertyWhenSetting(configurationObjectWrapper: SimplerConfigurationObjectWrapper,
                                                         configurationProperty: KMutableProperty<*>,
                                                         propertiesMap: MutableMap<String, Any?>,
                                                         configurationPropertyAnnotation: ConfigurationProperty
    ) {
        ConfigurationPropertyAgainstPropertyInConfigValidator(
            configurationObjectWrapper, configurationProperty,
            propertiesMap, configurationPropertyAnnotation
        ).validate()
    }


}



