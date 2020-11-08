package at.wavywalk.simpler.configurator

import at.wavywalk.simpler.configurator.anotations.ConfigurationProperty
import at.wavywalk.simpler.configurator.exceptions.ConfigurationPropertyException
import kotlin.reflect.KMutableProperty

class ConfigurationPropertyAgainstPropertyInConfigValidator(
    val configurationObjectWrapper: SimplerConfigurationObjectWrapper,
    val configurationProperty: KMutableProperty<*>,
    val propertiesMap: MutableMap<String, Any?>,
    val configurationAnnotaion: ConfigurationProperty
) {

    @Throws
    fun validate() {
        configurationPropertyIsNotNullableAndPropertyInConfigIsNull()
    }

    private fun configurationPropertyIsNotNullableAndPropertyInConfigIsNull() {
        if (
            !propertiesMap.containsKey(configurationProperty.name)
            && !configurationAnnotaion.allowNull
        ) {
            throw ConfigurationPropertyException(
                "${configurationProperty.name} of configuration consumer class ${configurationObjectWrapper.configurationObject::class.qualifiedName}" +
                        "is not nullable and such configuration key is not provided in configuration file under ${configurationObjectWrapper.pathToConfigurationFile}"
            )
        }
        if (
            providedConfigPropertyValueIsNull(configurationProperty, propertiesMap)
            && !configurationAnnotaion.allowNull
        ) {
            throw ConfigurationPropertyException(
                "${configurationProperty.name} of configuration consumer class ${configurationObjectWrapper.configurationObject::class.qualifiedName}" +
                        "is not nullable and value of property in configuration file under ${configurationObjectWrapper.pathToConfigurationFile} is null"
            )
        }
        ensureTypeCompatibility()
    }

    private fun providedConfigPropertyValueIsNull(configurationProperty: KMutableProperty<*>, propertiesMap: MutableMap<String, Any?>): Boolean {
        val valueAtPropertyInConfig = propertiesMap.getOrDefault(configurationProperty.name, null)
        return valueAtPropertyInConfig == null
    }

    private fun ensureTypeCompatibility() {
        val configurationPropertyReturnedTypeName = configurationProperty.returnType.toString().split(".").last().replace("?", "")
        val typeOfProvidedPropertyInConfigurationFile = getSimpleNameOfConfigurationPropertyType()
        if (configurationAnnotaion.allowNull && typeOfProvidedPropertyInConfigurationFile == null) {
            return
        }
        if (configurationPropertyReturnedTypeName != typeOfProvidedPropertyInConfigurationFile) {
            throw ConfigurationPropertyException(
                "${configurationProperty.name} of configuration consumer class ${configurationObjectWrapper.configurationObject::class.qualifiedName}" +
                        "has type of: ${configurationPropertyReturnedTypeName}," +
                        "while property in configuration file under ${configurationObjectWrapper.pathToConfigurationFile} has type of ${typeOfProvidedPropertyInConfigurationFile}"
            )
        }
    }

    private fun getSimpleNameOfConfigurationPropertyType(): String? {
        val valueOfConfigarationPropertyInConfigurationFile = propertiesMap.getOrDefault(configurationProperty.name, null)
        if (valueOfConfigarationPropertyInConfigurationFile == null) {
            return null
        }
        return valueOfConfigarationPropertyInConfigurationFile::class.simpleName
    }

}