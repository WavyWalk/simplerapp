package at.wavywalk.simpler.configurator.anotations

@Target(AnnotationTarget.PROPERTY)
annotation class ConfigurationProperty(
    val allowNull: Boolean = false,
    val allowDefault: Boolean = false
)