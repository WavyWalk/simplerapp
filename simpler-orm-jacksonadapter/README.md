# What's it?
A Jackson adapter for simpler-orm json serialization.
# how to install
```kotlin
//build.gradle
repositories {
    mavenCentral()
    maven {
        url  "https://dl.bintray.com/wavywalk/maven" //add this repo
    }
}

dependencies {
    compile 'at.wavywalk.simpler:simpler-orm-jacksonadapter:0.1.0:sources'
    compile 'at.wavywalk.simpler:simpler-orm-jacksonadapter:0.1.0'
}
```
# How to use with simpler-orm
When building `SimplerOrmDependenciesProvider`:
```kotlin

SimplerOrmDependenciesManager.provider = SimplerOrmDependenciesProvider(
            jsonObjectMapper = JacksonObjectMapperAdapter(), //Pass HERE
            defaultDslContext = dsl 
        )

```
```kotlin
SimplerDependencyManager.provider = SimplerDependenciesProvider(
            //servletRequestParametersWrapper =  ServletRequestParametersWrapper(
            //    jsonParametersParser = JacksonParametersParser(ObjectMapper())
            //),
            //assetsPathProvider = AssetsPathProvider(publicFolderConfig),
            templateProcessor = FreemarkerTemplateProcessor(configuration) //pass here/ configuration is the configuration object that your suposed to build on your own
        )
```

That's it. It does nothing on it's own