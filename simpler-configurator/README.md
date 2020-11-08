# What's it, quickly!
Simple configuration lib for Kotlin. That allows your POJOS to consume configuration files.
# Dependencies
It is coded against interface. For now the Yaml adapter is provided.
# How
Define a POJO that acts as config object or use existing. Annotatate properties that needed to be configured with configuration file with `@ConfigurationProperty`

call it with adaptered consumer, provided with file path.

properties are typed as you've defined them.

# installation
```kotlin
//build.gradle
repositories {
    mavenCentral()
    maven {
        url  "https://dl.bintray.com/wavywalk/maven" //add this repo
    }
}

dependencies {
    compile 'at.wavywalk.simpler:simpler-configurator:0.1.0:sources'
    compile 'at.wavywalk.simpler:simpler-configurator:0.1.0'
}
```

# Show me the code!
```kotlin
class DatabaseConfiguration {

    @ConfigurationProperty
    var username: String? = null

    @ConfigurationProperty(allowDefault = true)
    var password: String? = "foo"

    @ConfigurationProperty(allowNull = true)
    var jdbcUrl: String? = null

    @ConfigurationProperty
    var driverClassName: String? = null
    
    @ConfigurationProperty
    var port: Int? = null

}
```
configuration file: `db_config.yaml` at ./
```yaml
development:
  username: postgres
  password: postgres
  jdbcUrl: jdbc:postgresql://localhost/test_app
  driverClassName: org.postgresql.Driver
  port: 0

production:
  auth_server:
    username: postgres
    password: postgres
    jdbcUrl: jdbc:postgresql://localhost/test_app
    driverClassName: org.postgresql.Driver
    port: 0
  
  
```

```kotlin
val dbConfig = DatabaseConfiguration()
YamlConfigurationConsumer.consume(
    objectToConfigure = it,
    pathToConfigurationFile = "database.yml",
    "development" // vararg keyPathToConfigurationPropertiesInFile // allowing nested configurations, e.g.: "production", "auth_server" // or provide it wih dep injector like App.env etc
)
db.config.username!! // postgres
```
# Drawbacks
properties should be provided as nullable vars. Well it could be implemented through delegates/lazy.
but there'd be problems with primitive types or it'd require casting at runtime etc.
nobody's got time for dat.


